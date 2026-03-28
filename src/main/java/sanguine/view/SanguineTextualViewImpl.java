package sanguine.view;

import sanguine.model.Sanguine;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * This is the implementation of the SanguineTextualView interface. It is designed to produce a
 * textual representation of a Sanguine game.
 */
public class SanguineTextualViewImpl implements SanguineTextualView {
  private final Sanguine model;

  /**
   * This is the constructor for this class. Initialises any fields to their respective values.
   *
   * @param model is the model of the Sanguine game that is going to be turned into String.
   * @throws IllegalArgumentException if model is null.
   */
  public SanguineTextualViewImpl(Sanguine model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    String result = "";
    for (int rows = 0; rows < this.model.getRows(); rows++) {
      for (int cols = 0; cols < this.model.getCols(); cols++) {
        if (cols == 0) {
          result += this.model.rowScore(rows, Player.PLAYER1) + " ";
        }
        BoardInput input = this.model.getInputAt(rows, cols);
        if (input != null) {
          if (input.getCard() != null) {
            result += input.getPlayer().getValue();
          }
          if (input.getPawns() != null) {
            result += input.getPawns().getValue();
          }
        } else {
          result += "_";
        }
      }
      result += " " + this.model.rowScore(rows, Player.PLAYER2) + System.lineSeparator();
    }
    return result;
  }
}
