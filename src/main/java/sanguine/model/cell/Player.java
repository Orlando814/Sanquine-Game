package sanguine.model.cell;

/**
 * enumeration to reference the two players, p1 "red" p2 "blue".
 */
public enum Player {
  PLAYER1("R"),
  PLAYER2("B");

  private final String name;

  /**
   * constructor for the enum to instantiate it with the name of the color / player.
   *
   * @param name the name / player. We can reference equality since only us (the developer)
   *             will be inputting the values for it so we don't need to check anything.
   */
  Player(String name) {
    this.name = name;
  }

  /**
   * will get the string value of the current enumeration (p1 is red, p2 is blue).
   *
   * @return the string value of the current enum.
   */
  public String getValue() {
    return this.name;
  }

  /**
   * will just return the name of the Player.
   *
   * @return will return the name of the player, either red or blue.
   */
  @Override
  public String toString() {
    if (this.name.equals("B")) {
      return "Player 2";
    }
    return "Player 1";
  }
}
