package sanguine.model;

import java.util.List;
import sanguine.model.card.Card;

/**
 * represents the Sanguine interface. Will hold all the public methods such as making a move,
 * checking if the game is over, and other relevant things.
 */
public interface Sanguine extends ReadOnlySanguine {

  /**
   * represents the starting of the game. Will make sure that we have valid rows and columns and
   * throw illegal argument exceptions as necessary.
   *
   * @param rows          the inputted amount of rows that our board will have.
   * @param cols          the inputted amount of columns that our code will have.
   * @param playerOneDeck the inputted deck for player 1.
   * @param playerTwoDeck the inputted deck for player 2.
   * @throws IllegalArgumentException When the row or column is < 0 and when the columns aren't odd.
   */
  void startGame(int rows, int cols, List<Card> playerOneDeck, List<Card> playerTwoDeck)
      throws IllegalArgumentException;

  /**
   * Will place the card at the specified row and column.
   *
   * @param row the row, indexed at 0
   * @param col the column, indexed at 0
   * @throws IllegalArgumentException will throw if there is an invalid row or column. that meaning
   *                                  it is too large for the size of our board or negative.
   * @throws IllegalStateException    will throw if the move we attempt to do is invalid.
   */
  void placeCard(Card c, int row, int col)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * will take in a player and then pass their move if they so desire.
   *
   */
  void passMove();
}