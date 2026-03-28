package sanguine.model.deck;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.card.CardInfluence;
import sanguine.model.card.CardReader;
import sanguine.model.card.Cost;
import sanguine.model.card.ReadCardFile;
import sanguine.model.cell.Player;

/**
 * This class is the implementation of the DeckCreator interface. It is designed to create a deck
 * for the model.
 */
public class DeckCreatorImpl implements DeckCreator {

  /**
   * This method creates a full formed deck. Does this by first reading the file and then passing
   * it to a CardReader which will tokenize the file. Then the tokens are iterated through and
   * various data about the card is used to create the card which is then added to a list if player
   * is red. If player is blue then the rows are reversed as the blue player plays on the other side
   * of the board.
   *
   * @param player is the player that this deck is for.
   * @param path is a String representing the path to the deck config file.
   * @return a list of Cards representing the deck that will be used in the model.
   */
  public List<Card> createDeck(Player player, String path) {
    CardReader cardInfo = new ReadCardFile(new File(path));
    List<Card> deck = new ArrayList<>();
    for (int cardIndex = 0; cardIndex < cardInfo.showCards().size(); cardIndex++) {
      String name = cardInfo.showNameValueCost(cardIndex, "name");
      Cost cost = Cost.getCost(Integer.parseInt(cardInfo.showNameValueCost(cardIndex,
          "cost")));
      int value = Integer.parseInt(cardInfo.showNameValueCost(cardIndex, "value"));
      List<List<CardInfluence>> redInfluence = cardInfo.showInfluence(cardIndex);

      this.addCard(name, cost, value, redInfluence, deck, player);
    }
    return deck;
  }

  /**
   * This will add a card to the given deck based on the given player type. Uses a switch statement
   * to determine how to add the card. If the player is red the card is added, but if the player
   * is blue then all the rows of the influence are reversed since the blue player starts on the
   * opposite side of the board.
   *
   * @param name      is the name of the card.
   * @param cost      is the cost of the card.
   * @param value     is the value of the card.
   * @param influence is the grid representing the card's influence.
   * @param deck      is the current deck of cards.
   * @param player    is the player this deck belongs too.
   */
  private void addCard(String name, Cost cost, int value, List<List<CardInfluence>> influence,
                       List<Card> deck, Player player) {
    switch (player) {
      case Player.PLAYER1 -> checkCardCount(name, cost, value, deck, influence);
      case Player.PLAYER2 -> {
        List<List<CardInfluence>> blueInfluence = new ArrayList<>();
        for (List<CardInfluence> row : influence) {
          blueInfluence.add(row.reversed());
        }
        checkCardCount(name, cost, value, deck, blueInfluence);
      }
      default -> throw new IllegalArgumentException("Unknown player " + player);
    }
  }

  /**
   * will check to see if we have too many of a given card in our deck before adding it (3).
   *
   * @param name      is the name of the card.
   * @param cost      is the cost of the card.
   * @param value     is the value of the card.
   * @param influence is the grid representing the card's influence.
   * @param deck      is the current deck of cards.
   */
  private void checkCardCount(String name, Cost cost, int value, List<Card> deck,
                              List<List<CardInfluence>> influence) {
    BasicCard temp = new BasicCard(name, cost, value, influence);
    int cardCount = 0;
    for (Card card : deck) {
      BasicCard bc = (BasicCard) card;
      if (bc.equals(temp)) {
        cardCount++;
      }
    }
    if (cardCount >= 2) {
      throw new IllegalArgumentException("Cannot have more than 3 of a given card");
    } else {
      deck.add(temp);
    }
  }
}
