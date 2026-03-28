package sanguine.controller;

import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;
import sanguine.strategy.Strategy;

/**
 * This represents the implementation of a machine player. This will take in a strategy to determine
 * the moves that this player will make.
 */
public class MachineImpl implements PlayerAction {
  private final Strategy strategy;
  private final Player player;
  private PlayerActionsListener listener;

  /**
   * A regular constructor for this class that takes in a strategy for its arguments.
   *
   * @param strategy is the strategy this machine player will be using to make moves.
   * @param player is the player this implementation is tied to.
   * @throws IllegalArgumentException if one of the given arguments is null.
   */
  public MachineImpl(Player player, Strategy strategy) {
    if (strategy == null || player == null) {
      throw new IllegalArgumentException("Arguments can't be null.");
    }
    this.strategy = strategy;
    this.player = player;
  }


  @Override
  public void makeMove(ReadOnlySanguine model) {
    this.listener.hasMoveBeenMade(this.strategy.implementMove(model, this.player));
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public void subscribe(PlayerActionsListener listener) {
    this.listener = listener;
  }
}
