package sanguine.model.deck;

import java.util.List;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;

/**
 * interface to define our public behavior for DeckCreator. The only behavior is that there will
 * be a createDeck for a given player utilizing a specific path.
 */
public interface DeckCreator {

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
  List<Card> createDeck(Player player, String path);
}
