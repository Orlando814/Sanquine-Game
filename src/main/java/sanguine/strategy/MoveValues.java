package sanguine.strategy;

import java.util.Objects;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;

/**
 * will represent the card that we are moving, the player who we are moving for, and the row
 * and column that we are moving to. Allows for us to return this object instead of just one
 * value.
 */
public class MoveValues {

  final int row;
  final int col;
  final Player player;
  final Card card;

  /**
   * we do NOT need to null check any of these since we will be only passing in input from our
   * strategy, the user cannot mess with this.
   *
   * @param row    the relevant row from 0 to max rows - 1.
   * @param col    the relevant col from 0 to max cols - 1.
   * @param player the player who we are doing the move for.
   * @param card   the card that we are trying to move.
   */
  public MoveValues(int row, int col, Player player, Card card) {
    this.row = row;
    this.col = col;
    this.player = player;
    this.card = card;
  }

  /**
   * will just return the given row to this object.
   *
   * @return the row of the object.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * will just return the given col to this object.
   *
   * @return the col of the object.
   */
  public int getCol() {
    return this.col;
  }

  /**
   * will just return the given player to this object.
   *
   * @return the player of the object.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * will just return the given card to this object.
   *
   * @return the card of the object.
   */
  public Card getCard() {
    return this.card;
  }

  /**
   * Overwrites the equals method for this class.
   *
   * @param obj the reference object with which to compare.
   * @return a boolean saying if the given object is equal to this.
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MoveValues)) {
      return false;
    }
    MoveValues that = (MoveValues) obj;
    return this.row == that.row && this.col == that.col
        && this.player == that.player && this.card == that.card;
  }

  /**
   * Overwrites the hashcode method of this to be based on the fields of this class and not its
   * reference.
   *
   * @return a unique int that represents this.
   */
  public int hashCode() {
    return Objects.hash(this.row, this.col, this.player, this.card);
  }

}
