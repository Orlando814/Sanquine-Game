package sanguine.strategy;

import java.util.List;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * this will be the class that does the greedy move. If it "fails" it will then go into the
 * place first move.
 */
public class GreedyMove implements Strategy {

  /**
   * will pick the best move using our AI, this will basically start by maximizing the score,
   * the go back on trying to do the first move, and will pass if no move can be done.
   *
   * @param model  the read only version of the model that we are using to read the game.
   * @param player the player that we are making the relevant move for.
   * @return the MoveValues, an object which contains the row, col, player, and card that we are
   *      moving for / with.
   */
  public MoveValues implementMove(ReadOnlySanguine model, Player player) {
    MoveValues greedy = greedyAttempt(model, player);
    //check for if we should do our fallback strategy
    if (greedy == null) {
      //fallback
      return new FirstMove().implementMove(model, player);
    } else {
      return greedy;
    }
  }

  private MoveValues greedyAttempt(ReadOnlySanguine model, Player player) {
    if (player == Player.PLAYER1) {
      return greedyAttemptP1(model, player);
    } else {
      return greedyAttemptP2(model, player);
    }
  }

  /**
   * helper method to actually do the greedy move and see if there is anything we can do in this
   * manner.
   *
   * @param model  the model that we are using to look at the board (read only!).
   * @param player the player that we are making the move for.
   * @return the MoveValues, an object which gives us all necessary information to do the move.
   */
  private MoveValues greedyAttemptP1(ReadOnlySanguine model, Player player) {
    //we can just go left to right and check if there is a pawn at the location and see
    //what happens if we place the pawn
    List<Card> hand = model.getHand(player);
    int scoreIncrease = 0;
    for (Card card : hand) {
      BasicCard bc = (BasicCard) card;
      for (int row = 0; row < model.getRows(); row++) {
        for (int col = 0; col < model.getCols(); col++) {
          if (model.getInputAt(row, col) != null) {
            if (model.isValidMove(bc, row, col, player)) {
              if (scoreIncrease < checkScoreChange(model, bc, row, player)) {
                return new MoveValues(row, col, player, card);
              }
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * helper method to actually do the greedy move and see if there is anything we can do in this
   * manner.
   *
   * @param model  the model that we are using to look at the board (read only!).
   * @param player the player that we are making the move for.
   * @return the MoveValues, an object which gives us all necessary information to do the move.
   */
  private MoveValues greedyAttemptP2(ReadOnlySanguine model, Player player) {
    //we can just go left to right and check if there is a pawn at the location and see
    //what happens if we place the pawn
    List<Card> hand = model.getHand(player);
    int scoreIncrease = 0;
    for (Card card : hand) {
      BasicCard bc = (BasicCard) card;
      for (int row = 0; row < model.getRows(); row++) {
        for (int col = model.getCols() - 1; col > -1; col--) {
          if (model.getInputAt(row, col) != null) {
            if (model.isValidMove(bc, row, col, player)) {
              if (scoreIncrease < checkScoreChange(model, bc, row, player)) {
                return new MoveValues(row, col, player, card);
              }
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * will check to see how much the score changes by doing this given move. We are assuming this
   * move can be done as it was checked in the method above.
   *
   * @param model the model that we are using in this instance.
   * @param bc the basicCard that is relevant to our move.
   * @param row the row we are moving this card into.
   * @param player the player that we are making the move for.
   * @return the integer amount that the score increases by
   */
  private int checkScoreChange(ReadOnlySanguine model, BasicCard bc, int row, Player player) {
    int curPlayerScore = model.rowScore(row, player);
    int otherPlayerScore;
    int cardValue = bc.getValue();
    if (player == Player.PLAYER1) {
      otherPlayerScore = model.rowScore(row, Player.PLAYER2);
    } else {
      otherPlayerScore = model.rowScore(row, Player.PLAYER1);
    }
    if ((curPlayerScore > otherPlayerScore)
        || curPlayerScore == otherPlayerScore) {
      return 0; //this is because we want our implementation to only worry about increasing past
      //row score and I accidentally just made a normal greedy algorithm
    }
    int diff = otherPlayerScore - curPlayerScore;
    if (diff < cardValue) {
      return cardValue - diff;
    }
    return 0;
  }
}
