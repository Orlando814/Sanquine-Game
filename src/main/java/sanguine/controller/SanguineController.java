package sanguine.controller;

/**
 * This is the controller for the Sanguine game. Is responsible for listening to the view and then
 * changing the model to represent the user's actions.
 */
public interface SanguineController {

  /**
   * will play a full gamme of sanguine from start until end.
   */
  void playGame();
}
