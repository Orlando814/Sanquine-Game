package sanguine.model;

import java.util.List;
import sanguine.controller.ModelFeaturesListener;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * This represents a read-only version of the model. Is intended to be used by the view as the view
 * should never be able to mutate the model. It should only be able to observe it.
 */
public class ReadOnlyBasicSanguine implements ReadOnlySanguine {

  private final Sanguine model;

  /**
   * constructor for this read only implementation.
   *
   * @param model the model that we are using in our game.
   */
  public ReadOnlyBasicSanguine(Sanguine model) {
    if (model == null) {
      throw new IllegalArgumentException("model cant be null");
    }
    this.model = model;
  }

  /**
   * will get the score of a given row of the board.
   *
   * @param row the inputted row indexed at 0
   * @param p   the player that we want to calculate the score for
   * @return the score of the given row.
   */
  @Override
  public int rowScore(int row, Player p) {
    return this.model.rowScore(row, p);
  }

  /**
   * the total score for the given player.
   *
   * @param p the given player that we want the score of
   * @return the given score of that player as a total num
   */
  @Override
  public int totalScore(Player p) {
    return this.model.totalScore(p);
  }

  /**
   * will determine if the game is over, basically if both of the players passed consecutively.
   *
   * @return a boolean saying if the game is over or not.
   */
  @Override
  public Boolean isGameOver() {
    return this.model.isGameOver();
  }

  /**
   * will, when called, ONLY WHEN THE GAME IS OVER, return the player that won the game.
   *
   * @return the player who has won the game itself.
   */
  @Override
  public Player whoWon() {
    return this.model.whoWon();
  }

  /**
   * will get the amount of rows IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  @Override
  public int getRows() {
    return this.model.getRows();
  }

  /**
   * will get the amount of cols IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  @Override
  public int getCols() {
    return this.model.getCols();
  }

  /**
   * will get the given BoardInput at the row and col specified. Will only do so if the row and
   * col are valid inputs.
   *
   * @param row the row indexed at 0.
   * @param col the col indexed at 0.
   * @return the BoardInput class which is silly.
   */
  @Override
  public BoardInput getInputAt(int row, int col) {
    return this.model.getInputAt(row, col);
  }

  /**
   * will get the handSize of the given player at any given moment, mainly, this method is for
   * testing.
   *
   * @param p the player that we want the size of the hand of.
   * @return an integer representing the size of the hand.
   */
  @Override
  public int getHandSize(Player p) {
    return this.model.getHandSize(p);
  }

  /**
   * will get the hand for a given player and return it.
   *
   * @param p the player that we are getting the hand for.
   * @return the hand itself as a list of cards.
   */
  @Override
  public List<Card> getHand(Player p) {
    return this.model.getHand(p);
  }

  /**
   * will get the hand for the given player.
   *
   * @param p     the players hand we are referencing.
   * @param index the index within their hand that we are referencing.
   * @return a copy of the card that is being returned.
   */
  @Override
  public Card getCardInHand(Player p, int index) {
    return this.model.getCardInHand(p, index);
  }

  /**
   * will get the player for a given turn.
   *
   * @return the current player whose turn it is.
   */
  @Override
  public Player getPlayer() {
    return this.model.getPlayer();
  }

  /**
   * will check to see if a given move is valid for a card.
   *
   * @param c the relevant card that we are using.
   * @param row the row that we are trying to move the card to.
   * @param col the col that we are trying to move the card to.
   * @param player the player we are checking the move of.
   * @return the boolean that represents if the move is valid or not.
   */
  @Override
  public boolean isValidMove(BasicCard c, int row, int col, Player player) {
    return this.model.isValidMove(c, row, col, player);
  }

  @Override
  public void subscribe(ModelFeaturesListener listener) {
    this.model.subscribe(listener);
  }
}
