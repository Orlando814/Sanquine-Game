package sanguine.controller;

import sanguine.model.cell.Player;

/**
 * interface that holds the reference to the on mouse / key click. Represents a listener whose
 * listening to specific view events.
 */
public interface ViewFeaturesListener {

  /**
   * will execute based on if a mouse event occurred in the board frame.
   *
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  void mouseEventBoard(int x, int y);

  /**
   * Will execute based on if a mouse event occurred in the hand frame.
   *
   * @param cardIndex is the index of the card the user pressed in the hand.
   * @param player is the player that this hand belong to.
   */
  void mouseEventHand(int cardIndex, Player player);

  /**
   * will determine what happens depending on what the key pressed is.
   *
   * @param key the key that the user pressed.
   */
  void keyClicked(String key);
}

