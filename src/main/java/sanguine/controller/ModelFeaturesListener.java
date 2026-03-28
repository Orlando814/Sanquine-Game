package sanguine.controller;

import sanguine.model.cell.Player;

/**
 * Represents a listener that is listening to the model. Holds reference to specifically whose turn
 * it is.
 */
public interface ModelFeaturesListener {

  /**
   * Listening for whose turn it currently is.
   *
   * @param player is the player whose turn it currently is.
   */
  void whoseTurn(Player player);

  /**
   * listening for whether the game ended or not, it will also return the player who won the game.
   *
   * @param player the player who won the game.
   */
  void gameOver(Player player);
}
