package sanguine;

import java.util.List;
import sanguine.model.BasicSanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.view.SanguineTextualView;
import sanguine.view.SanguineTextualViewImpl;

/**
 * This class is where a main method is to run textual UI for the Sanguine game.
 */
public final class Sanguine {

  /**
   * This is the main method that runs the Sanguine textual UI version of the game.
   *
   * @param args is the arguments inputted by the user.
   */
  public static void main(String[] args) {

    if (args.length == 1) {
      try {
        sanguine.model.Sanguine model = new BasicSanguine();
        List<Card> redDeck = new DeckCreatorImpl().createDeck(Player.PLAYER1, args[0]);
        List<Card> blueDeck = new DeckCreatorImpl().createDeck(Player.PLAYER2, args[0]);
        model.startGame(3, 5, redDeck, blueDeck);
        SanguineTextualView view = new SanguineTextualViewImpl(model);
        System.out.print(view); //print
        System.out.println("Welcome!"); //welcome msg

        model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Security to 2, 1"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Security to 1, 5"); //move text

        model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Security to 1, 1"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).get(3), 1, 4);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Crab to 2, 5"); //move text

        model.placeCard(model.getHand(Player.PLAYER1).get(4), 2, 0);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Flame to 3, 1"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Security to 3, 5"); //move text

        System.out.println(model.getHand(Player.PLAYER1).get(3).toString());
        model.placeCard(model.getHand(Player.PLAYER1).get(3), 1, 1);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Wheel to 2, 2"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).get(3), 0, 3);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Flame to 1, 4"); //move text

        model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 1);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Crab to 1, 2"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).get(3), 2, 3);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Flame to 3, 4"); //move text

        model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 2);
        System.out.print(view); //print new model after move
        System.out.println("Player 1 moved Flame to 1, 3"); //move text

        model.placeCard(model.getHand(Player.PLAYER2).get(4), 1, 3);
        System.out.print(view); //print new model after move
        System.out.println("Player 2 moved Grenade to 2, 4"); //move text

        //both players pass since no valid moves they can make
        model.passMove();
        if (model.isGameOver()) {
          System.out.println("Game is over!");
        }
        model.passMove();

        if (model.isGameOver()) {
          System.out.println("Game is over!");
        }

        if (model.isGameOver()) {
          if (model.whoWon() == Player.PLAYER1) {
            System.out.println("Player 1 won! score: " + model.totalScore(Player.PLAYER1));
          } else {
            System.out.println("Player 2 won! score: " + model.totalScore(Player.PLAYER2));
          }
        }
      } catch (final IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } else {
      throw new IllegalArgumentException("Please input only deck config path");
    }
  }
}
