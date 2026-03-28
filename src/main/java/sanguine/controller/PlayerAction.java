package sanguine.controller;

import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;

/**
 * Represents a player that is either human or machine. These act as publishers and can make moves.
 */
public interface PlayerAction {

  /**
   * Determines the move that the player is going to make.
   *
   * @param model is the read only version of the model that the player can use to determine its
   *              move.
   */
  void makeMove(ReadOnlySanguine model);

  /**
   * Gets the player that is tied to the current implementation.
   *
   * @return the player that is tied to the current implementation.
   */
  Player getPlayer();

  /**
   * will subscriber to the publisher.
   *
   * @param listener listens and shi to the publisher.
   */
  void subscribe(PlayerActionsListener listener);

}
