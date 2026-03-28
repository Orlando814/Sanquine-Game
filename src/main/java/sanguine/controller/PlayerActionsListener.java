package sanguine.controller;

import sanguine.strategy.MoveValues;

/**
 * represents the actions listener for the player. This allows for both the human player
 * and the machine player to have a similar implementation where they run through the view.
 */
public interface PlayerActionsListener {

  /**
   * represents the listener that checks to see if a move was made and then passes the moveValues
   * through each given step.
   *
   * @param move the values to represent the moves.
   */
  void hasMoveBeenMade(MoveValues move);
}
