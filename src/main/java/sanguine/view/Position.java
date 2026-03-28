package sanguine.view;

/**
 * just a generic posn class. Will store the x and y so that I can store the row and column of the
 * positions.
 */
public class Position {

  private final int posnX;
  private final int posnY;

  /**
   * the generic constructor for this.
   *
   * @param x the x index (row)
   * @param y the y index (col)
   */
  public Position(int x, int y) {
    this.posnX = x;
    this.posnY = y;
  }

  /**
   * the reference for the x coordinate of the posn.
   *
   * @return the x coordinate of the posn.
   */
  public int getX() {
    return this.posnX;
  }

  /**
   * the  reference for the y coordinate of the posn.
   *
   * @return the y coordinate of the posn.
   */
  public int getY() {
    return this.posnY;
  }
}
