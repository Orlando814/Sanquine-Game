package sanguine.strategy;

import java.util.List;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;

/**
 * this will be the second thing that we try after our greedy move. If there is no benefit to
 * a greedy move, aka no score increase, we will the first index of a card at the first pawns
 * where it is allowed to be put. If this isn't possible, there are no moves, and we will go to our
 * final fallback strategy.
 */
public class FirstMove implements Strategy {

  /**
   * will pick the best move using our AI, this will basically start by maximizing the score,
   * the go back on trying to do the first move, and will pass if no move can be done.
   *
   * @param model  the read only version of the model that we are using to read the game.
   * @param player the player that we are making the relevant move for.
   * @return the MoveValues, an object which contains the row, col, player, and card that we are
   *      moving for / with.
   */
  @Override
  public MoveValues implementMove(ReadOnlySanguine model, Player player) {
    MoveValues firstMove = firstMoveAttempt(model, player);
    //check for if we should do our fallback strategy
    if (firstMove == null) {
      //fallback
      return new PassMove().implementMove(model, player);
    } else {
      return firstMove;
    }
  }

  private MoveValues firstMoveAttempt(ReadOnlySanguine model, Player player) {
    if (player == Player.PLAYER1) {
      return firstMoveAttemptP1(model, player);
    } else {
      return firstMoveAttemptP2(model, player);
    }
  }

  /**
   * will make the relevant move to see check the first move that can be played with the first card
   * from left to right.
   *
   * @param model  the relevant model that we are using.
   * @param player the relevant player we are doing this move for.
   * @return the moveValues for our move.
   */
  private MoveValues firstMoveAttemptP1(ReadOnlySanguine model, Player player) {
    List<Card> hand = model.getHand(player);
    for (Card card : hand) {
      BasicCard bc = (BasicCard) card;
      for (int row = 0; row < model.getRows(); row++) {
        for (int col = 0; col < model.getCols(); col++) {
          //check if move is valid, if it is just return it immediately
          if (model.getInputAt(row, col) == null) {
            continue;
          } else {
            if (model.isValidMove(bc, row, col, player)) {
              return new MoveValues(row, col, player, card);
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * will make the relevant move to see check the first move that can be played with the first card
   * from left to right.
   *
   * @param model  the relevant model that we are using.
   * @param player the relevant player we are doing this move for.
   * @return the moveValues for our move.
   */
  private MoveValues firstMoveAttemptP2(ReadOnlySanguine model, Player player) {
    List<Card> hand = model.getHand(player);
    for (Card card : hand) {
      BasicCard bc = (BasicCard) card;
      for (int row = 0; row < model.getRows(); row++) {
        for (int col = model.getCols() - 1; col > -1; col--) {
          //check if move is valid, if it is just return it immediately
          if (model.getInputAt(row, col) == null) {
            continue;
          } else {
            if (model.isValidMove(bc, row, col, player)) {
              return new MoveValues(row, col, player, card);
            }
          }
        }
      }
    }
    return null;
  }
}
