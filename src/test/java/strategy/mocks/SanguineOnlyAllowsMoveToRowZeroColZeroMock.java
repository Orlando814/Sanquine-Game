package strategy.mocks;

import java.util.List;
import sanguine.controller.ModelFeaturesListener;
import sanguine.model.Sanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * will check to make sure that the card is being moved to 0 0 and not any other location.
 */
public class SanguineOnlyAllowsMoveToRowZeroColZeroMock implements Sanguine {

  Appendable log;
  Sanguine model;

  /**
   * This is the constructor for this class that checks if the given Appendable isn't a null value.
   *
   * @param log is the Appendable that will record all of the moves made using this mock.
   */
  public SanguineOnlyAllowsMoveToRowZeroColZeroMock(Appendable log, Sanguine model) {
    if (log == null || model == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.log = log;
    this.model = model;
  }

  @Override
  public void startGame(int rows, int cols, List<Card> playerOneDeck, List<Card> playerTwoDeck)
      throws IllegalArgumentException {
    model.startGame(rows, cols, playerOneDeck, playerTwoDeck);
  }

  @Override
  public void placeCard(Card c, int row, int col)
      throws IllegalArgumentException, IllegalStateException {
    model.placeCard(c, row, col);
  }

  @Override
  public void passMove() {
    model.passMove();
  }

  /**
   * will subscriber to the publisher.
   *
   * @param listener listens and shi to the publisher.
   */
  @Override
  public void subscribe(ModelFeaturesListener listener) {
    this.model.subscribe(listener);
  }

  @Override
  public int rowScore(int row, Player p) {
    return model.rowScore(row, p);
  }

  @Override
  public int totalScore(Player p) {
    return model.totalScore(p);
  }

  @Override
  public Boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public Player whoWon() {
    return model.whoWon();
  }

  @Override
  public int getRows() {
    return model.getRows();
  }

  @Override
  public int getCols() {
    return model.getCols();
  }

  @Override
  public BoardInput getInputAt(int row, int col) {
    return model.getInputAt(row, col);
  }


  @Override
  public int getHandSize(Player p) {
    return getHandSize(p);
  }

  @Override
  public List<Card> getHand(Player p) {
    return model.getHand(p);
  }

  @Override
  public Card getCardInHand(Player p, int index) {
    return model.getCardInHand(p, index);
  }

  @Override
  public Player getPlayer() {
    return model.getPlayer();
  }

  @Override
  public boolean isValidMove(BasicCard c, int row, int col, Player player) {
    return row == 0 && col == 0;
  }
}