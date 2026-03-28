package strategy.mocks;

import java.io.IOException;
import java.util.List;
import sanguine.controller.ModelFeaturesListener;
import sanguine.model.Sanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * This is a mock of the Sanguine model that is intended to show the moves a strategy makes
 * as it plays the game. Used for testing.
 */
public class RecordModelMethodCallsMock implements Sanguine {
  Appendable log;
  Sanguine model;

  /**
   * This is the constructor for this class that checks if the given Appendable isn't a null value.
   *
   * @param log is the Appendable that will record all of the moves made using this mock.
   */
  public RecordModelMethodCallsMock(Appendable log, Sanguine model) {
    if (log == null || model == null) {
      throw new IllegalArgumentException("Appendable and model cannot be null");
    }
    this.log = log;
    this.model = model;
  }

  @Override
  public void startGame(int rows, int cols, List<Card> playerOneDeck, List<Card> playerTwoDeck)
      throws IllegalArgumentException {
    try {
      log.append("Start game: Rows = ").append(String.valueOf(rows)).append(" Columns = ")
          .append(String.valueOf(cols)).append(System.lineSeparator());
      model.startGame(rows, cols, playerOneDeck, playerTwoDeck);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void placeCard(Card c, int row, int col)
      throws IllegalArgumentException, IllegalStateException {
    try {
      log.append("placeCard: Card = ").append(c.toString()).append("Row = ")
          .append(String.valueOf(row)).append(" Columns = ").append(String.valueOf(col))
          .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    model.placeCard(c, row, col);
  }

  @Override
  public void passMove() {
    try {
      log.append("passMove").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
    try {
      log.append("rowScore: row = ").append(String.valueOf(row)).append(" Player = ")
          .append(String.valueOf(p)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.rowScore(row, p);
  }

  @Override
  public int totalScore(Player p) {
    try {
      log.append("totalScore: Player = ").append(String.valueOf(p)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.totalScore(p);
  }

  @Override
  public Boolean isGameOver() {
    try {
      log.append("isGameOver").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.isGameOver();
  }

  @Override
  public Player whoWon() {
    try {
      log.append("whoWon").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.whoWon();
  }

  @Override
  public int getRows() {
    try {
      log.append("getRows").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getRows();
  }

  @Override
  public int getCols() {
    try {
      log.append("getCols").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getCols();
  }

  @Override
  public BoardInput getInputAt(int row, int col) {
    try {
      log.append("getInputAt: Row = ").append(String.valueOf(row)).append(" Columns = ")
          .append(String.valueOf(col)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getInputAt(row, col);
  }


  @Override
  public int getHandSize(Player p) {
    try {
      log.append("getHandSize: Player = ").append(String.valueOf(p)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getHandSize(p);
  }

  @Override
  public List<Card> getHand(Player p) {
    try {
      log.append("getHand: Player = ").append(String.valueOf(p)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getHand(p);
  }

  @Override
  public Card getCardInHand(Player p, int index) {
    try {
      log.append("getCardInHand: Player = ").append(String.valueOf(p)).append(" Index = ")
          .append(String.valueOf(index)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getCardInHand(p, index);
  }

  @Override
  public Player getPlayer() {
    try {
      log.append("getPlayer").append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.getPlayer();
  }

  @Override
  public boolean isValidMove(BasicCard c, int row, int col, Player player) {
    try {
      log.append("isValidMove: Card = ").append(String.valueOf(c)).append("Row = ")
          .append(String.valueOf(row)).append(" Columns = ").append(String.valueOf(col))
          .append(" Player = ").append(String.valueOf(player)).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.isValidMove(c, row, col, player);
  }
}