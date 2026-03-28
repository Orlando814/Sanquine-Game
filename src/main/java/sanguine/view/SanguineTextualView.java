package sanguine.view;

/**
 * This represents a textual view of the game. Only has a toString method as it will be displayed
 * on the terminal.
 */
public interface SanguineTextualView {

  /**
   * will make the toString for the given model.
   *
   * @return a string that represents the model.
   */
  String toString();
}
