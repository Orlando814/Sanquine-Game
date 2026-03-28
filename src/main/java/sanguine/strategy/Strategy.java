package sanguine.strategy;

import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;

/**
 * the interface that we are using to implement the strategy. We will create he necessary strat
 * using a greedy algorithm that will, if nothing happens, use first fit, and if unable to do first
 * fit, pass the move.
 */
public interface Strategy {

  /**
   * will pick the best move using our AI, this will basically start by maximizing the score,
   * the go back on trying to do the first move, and will pass if no move can be done.
   *
   * @param model the read only version of the model that we are using to read the game.
   * @param player the player that we are making the relevant move for.
   * @return the MoveValues, an object which contains the row, col, player, and card that we are
   *      moving for / with.
   */
  MoveValues implementMove(ReadOnlySanguine model, Player player);
}
