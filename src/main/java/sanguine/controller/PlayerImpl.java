package sanguine.controller;

import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;

/**
 * This represents the implementation of a human player. Since a human is making all the moves
 * there will not be a lot of implementation made.
 */
public class PlayerImpl implements PlayerAction {
  private final Player player;

  /**
   * This is the generic constructor for this class. Takes in which player this implementation is
   * tied to.
   *
   * @param player is the player this implementation is tied to.
   * @throws IllegalArgumentException if one of the given arguments are null.
   */
  public PlayerImpl(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("An argument is null");
    }
    this.player = player;
  }

  /**
   * stub implementation for the human player.
   *
   * @return the given player.
   */
  @Override
  public Player getPlayer() {
    return this.player;
  }

  /**
   * stub implementation for the human player.
   *
   * @param model is the read only version of the model that the player can use to determine its
   *              move.
   */
  @Override
  public void makeMove(ReadOnlySanguine model) {
  }

  /**
   * stub implementation for the human player.
   *
   * @param listener listens and shi to the publisher.
   */
  @Override
  public void subscribe(PlayerActionsListener listener) {
  }
}
