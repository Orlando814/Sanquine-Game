package sanguine.model;

import java.util.List;
import sanguine.controller.ModelFeaturesListener;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * represents the read only version of sanguine. Will be used for our view to disallow the player
 * from being able to mutate our model.
 */
public interface ReadOnlySanguine {

  /**
   * will subscriber to the publisher.
   *
   * @param listener listens and shi to the publisher.
   */
  void subscribe(ModelFeaturesListener listener);

  /**
   * will get the score of a given row of the board.
   *
   * @param row the inputted row indexed at 0
   * @param p the player that we want to calculate the score for
   * @return the score of the given row.
   */
  int rowScore(int row, Player p);

  /**
   * the total score for the given player.
   *
   * @param p the given player that we want the score of
   * @return the given score of that player as a total num
   */
  int totalScore(Player p);

  /**
   * will determine if the game is over, basically if both of the players passed consecutively.
   *
   * @return a boolean saying if the game is over or not.
   */
  Boolean isGameOver();

  /**
   * will, when called, ONLY WHEN THE GAME IS OVER, return the player that won the game.
   *
   * @return the player who has won the game itself.
   */
  Player whoWon();

  /**
   * will get the amount of rows IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  int getRows();

  /**
   * will get the amount of cols IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  int getCols();

  /**
   * will get the given BoardInput at the row and col specified. Will only do so if the row and
   * col are valid inputs.
   *
   * @param row the row indexed at 0.
   * @param col the col indexed at 0.
   * @return the BoardInput class which is silly.
   */
  BoardInput getInputAt(int row, int col);

  /**
   * will get the handSize of the given player at any given moment, mainly, this method is for
   * testing.
   *
   * @param p the player that we want the size of the hand of.
   * @return an integer representing the size of the hand.
   */
  int getHandSize(Player p);

  /**
   * will get the hand for a given player and return it.
   *
   * @param p the player that we are getting the hand for.
   * @return the hand itself as a list of cards.
   */
  List<Card> getHand(Player p);

  /**
   * will get the hand for the given player.
   *
   * @param p the players hand we are referencing.
   * @param index the index within their hand that we are referencing.
   * @return a copy of the card that is being returned.
   */
  Card getCardInHand(Player p, int index);

  /**
   * will get the player for a given turn.
   *
   * @return the current player whose turn it is.
   */
  Player getPlayer();

  /**
   * will check to see if a given move is valid for a card.
   *
   * @param c the relevant card that we are using.
   * @param row the row that we are trying to move the card to.
   * @param col the col that we are trying to move the card to.
   * @param player the player we are checking the move of.
   * @return the boolean that represents if the move is valid or not.
   */
  boolean isValidMove(BasicCard c, int row, int col, Player player);
}
