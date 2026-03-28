package sanguine.model.cell;

/**
 * represents the pawns within the board. Will either be 1, 2, or 3.
 */
public enum BoardInputsPawns {
  ONE(1),
  TWO(2),
  THREE(3);

  private final int pawns;

  /**
   * This is the constructor for this class. This initializes the fields to their respective values
   *
   * @param pawns is an int representing the pawns in a space.
   */
  BoardInputsPawns(int pawns) {
    switch (pawns) {
      case 1:
        this.pawns = 1;
        break;
      case 2:
        this.pawns = 2;
        break;
      default:
        this.pawns = 3;
        break;
    }
  }

  /**
   * will return the value pawns in a given square.
   *
   * @return the number of pawns itself from the enum.
   */
  public int getValue() {
    return this.pawns;
  }
}
