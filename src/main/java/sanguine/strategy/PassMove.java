package sanguine.strategy;

import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;

/**
 * final fallback strategy, it doesn't care what state the board is in, it will just pass the move.
 * This allows for our AI to always do something.
 */
public class PassMove implements Strategy {

  /**
   * will pick the best move using our AI, this will basically start by maximizing the score,
   * the go back on trying to do the first move, and will pass if no move can be done. This being
   * said, we will just check within our controller if the result of our Ai is null, if it is,
   * then we pass the move, otherwise we will make the move.
   *
   * @param model  the read only version of the model that we are using to read the game.
   * @param player the player that we are making the relevant move for.
   * @return the MoveValues, an object which contains the row, col, player, and card that we are
   *      moving for / with.
   */
  @Override
  public MoveValues implementMove(ReadOnlySanguine model, Player player) {
    return null;
  }
}
