package sanguine.view;

import sanguine.controller.ViewFeaturesListener;
import sanguine.model.cell.Player;
import sanguine.strategy.MoveValues;

/**
 * This interface represents a view for the Sanguine game that is a GUI instead of something
 * textual. We are using Swing to implement this GUI.
 */
public interface SanguineGuiView {

  /**
   * Refresh the view to reflect any changes in the game.
   */
  void refresh();

  /**
   * Make the view visible to start the game.
   */
  void makeVisible();

  /**
   * will subscriber to the publisher.
   *
   * @param listener listens and shi to the publisher.
   */
  void subscribe(ViewFeaturesListener listener);

  /**
   * This will highlight a card that the user has clicked.
   *
   * @param cardIndex is the index of the card that the user has clicked.
   */
  void clickCard(int cardIndex);

  /**
   * will set the posn in the board class to make sure that there is a way to highlight the location
   * of a given card.
   *
   * @param position the position we are doing this at.
   */
  void setPosn(Position position);

  /**
   * Will display when the user has made an invalid move.
   */
  void showInvalidMove();

  /**
   * * Will appear when the game has ended and show the winning player / their score.
   *
   * @param player is the player that won the game.
   * @param score  is the winning player's score.
   * @param type   is the type of end message (game over or tie)
   */
  void showGameOver(Player player, int score, String type);

  /**
   * This is the method that is called when the publisher says a move has been made. Delegates this
   * move down to the controller.
   *
   * @param move is the move the player has made.
   */
  void hasMoveBeenMade(MoveValues move);
}
