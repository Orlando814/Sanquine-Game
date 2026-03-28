package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.strategy.FirstMove;
import sanguine.strategy.GreedyMove;
import sanguine.strategy.MoveValues;
import sanguine.strategy.PassMove;
import strategy.mocks.RecordModelMethodCallsMock;
import strategy.mocks.SanguineOnlyAllowsMoveToRowZeroColZeroMock;
import strategy.mocks.SanguineOnlyAllowsMovesToRowTwoColThree;

/**
 * will test all of the strategies, making sure we pass when we should pass, making sure we
 * do moves when necessary etc. Will do this through checking if the objects have the proper
 * row, col, card and player.
 */
public class StrategyTests {

  //quick variables.
  Sanguine model = new BasicSanguine();
  List<Card> redDeck = new ArrayList<>();
  List<Card> blueDeck = new ArrayList<>();

  //vars to setup (just a basic normal game that works and I will mutate it later after each init)
  void init() {
    //model to use for testing’s sake
    model = new BasicSanguine();
    //default deck since we cant create cards :D
    DeckCreator create = new DeckCreatorImpl();
    redDeck = create.createDeck(Player.PLAYER1, "config/red.deck");
    blueDeck = create.createDeck(Player.PLAYER2, "config/red.deck");
  }

  /**
   * setup :D.
   */
  @Before
  public void setUp() {
    init();
  }

  @Test
  public void testMoveValuesEquals() {
    Card card1 = this.redDeck.getFirst();
    MoveValues move1 = new MoveValues(1, 2, Player.PLAYER1, card1);
    MoveValues move1Copy = new MoveValues(1, 2, Player.PLAYER1, card1);
    MoveValues move2 = new MoveValues(3, 7, Player.PLAYER1, card1);
    MoveValues move3 = new MoveValues(1, 2, Player.PLAYER2, card1);
    assertTrue(move1.equals(move1Copy));
    assertFalse(move1.equals(move2));
    assertFalse(move1.equals(move3));
    Card card2 = this.redDeck.get(2);
    MoveValues move4 = new MoveValues(1, 2, Player.PLAYER1, card2);
    assertFalse(move1.equals(move4));
    assertFalse(move2.equals(move3));
    assertFalse(move2.equals(move4));
    assertFalse(move3.equals(move4));
  }

  @Test
  public void testMoveValuesHashcode() {
    Card card1 = this.redDeck.getFirst();
    MoveValues move1 = new MoveValues(1, 2, Player.PLAYER1, card1);
    MoveValues move1Copy = new MoveValues(1, 2, Player.PLAYER1, card1);
    MoveValues move2 = new MoveValues(3, 7, Player.PLAYER1, card1);
    MoveValues move3 = new MoveValues(1, 2, Player.PLAYER2, card1);
    assertTrue(move1.hashCode() == move1Copy.hashCode());
    assertFalse(move1.hashCode() == move2.hashCode());
    assertFalse(move1.hashCode() == move3.hashCode());
    Card card2 = this.redDeck.get(2);
    MoveValues move4 = new MoveValues(1, 2, Player.PLAYER1, card2);
    assertFalse(move2.hashCode() == move4.hashCode());
    assertFalse(move2.hashCode() == move3.hashCode());
    assertFalse(move3.hashCode() == move4.hashCode());
    assertFalse(move1.hashCode() == move4.hashCode());
  }

  //test to make sure that the AI picks the first valid move that maximizes the score as much as
  //possible in a given row
  @Test
  public void testMoveToFirstSpotWithFirstCardDoesNotGetOverridden() {
    model.startGame(3, 5, redDeck, blueDeck);
    MoveValues vals = new GreedyMove().implementMove(model, Player.PLAYER1);
    assertEquals(0, vals.getRow());
    assertEquals(0, vals.getCol());
    assertEquals(Player.PLAYER1, vals.getPlayer());
    assertEquals("Security", ((BasicCard) vals.getCard()).getName());
    assertEquals(1, ((BasicCard) vals.getCard()).getValue());
  }

  //tests to make sure that the first move after doing a move is the next spot with the best card
  @Test
  public void testMoveToFirstSpotWithGreedyIncreasesScoreProperly() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 0, 0);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0), 0, 4);
    MoveValues vals = new GreedyMove().implementMove(model, Player.PLAYER1);
    assertEquals(0, vals.getRow());
    assertEquals(1, vals.getCol());
    assertEquals(Player.PLAYER1, vals.getPlayer());
    assertEquals("Security", ((BasicCard) vals.getCard()).getName());
    assertEquals(1, ((BasicCard) vals.getCard()).getValue());
  }

  @Test
  public void testGreedyWillDelegateToPassingMoveWithNoValidMoves() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 1, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(4), 2, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(3), 1, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 0, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 2, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 2);
    model.placeCard(model.getHand(Player.PLAYER2).get(4), 1, 3);
    MoveValues vals = new GreedyMove().implementMove(model, Player.PLAYER1);
    assertNull(vals);
  }

  @Test
  public void testFirstMoveWillDelegateToPassingMoveWithNoValidMoves() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 1, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(4), 2, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(3), 1, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 0, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 2, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 2);
    model.placeCard(model.getHand(Player.PLAYER2).get(4), 1, 3);
    MoveValues vals = new FirstMove().implementMove(model, Player.PLAYER1);
    assertNull(vals);
  }

  @Test
  public void testPassMoveWillPassMoveWithNoValidMoves() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 1, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(4), 2, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4);
    model.placeCard(model.getHand(Player.PLAYER1).get(3), 1, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 0, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 1);
    model.placeCard(model.getHand(Player.PLAYER2).get(3), 2, 3);
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 2);
    model.placeCard(model.getHand(Player.PLAYER2).get(4), 1, 3);
    MoveValues vals = new PassMove().implementMove(model, Player.PLAYER1);
    assertNull(vals);
  }

  //make sure that the first card will first fit in row -> col
  @Test
  public void testMoveToFirstSpotWithFirstIncreasesScoreProperly() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 0, 0);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0), 0, 4);
    MoveValues vals = new FirstMove().implementMove(model, Player.PLAYER1);
    assertEquals(0, vals.getRow());
    assertEquals(1, vals.getCol());
    assertEquals(Player.PLAYER1, vals.getPlayer());
    assertEquals("Security", ((BasicCard) vals.getCard()).getName());
    assertEquals(1, ((BasicCard) vals.getCard()).getValue());
  }

  @Test
  public void testMoveToFirstSpotWithFirstCardDoesNotGetOverriddenWithMethodCalls() {
    model.startGame(3, 5, redDeck, blueDeck);
    StringBuilder log = new StringBuilder();
    Sanguine transcript = new RecordModelMethodCallsMock(log, model);
    MoveValues vals = new GreedyMove().implementMove(transcript, Player.PLAYER1);
    String[] moves = log.toString().split(System.lineSeparator());
    assertEquals("getInputAt: Row = 0 Columns = 0", moves[3]);
    assertEquals("getHand: Player = Player 1", moves[0]);
    assertEquals("isValidMove: Card = Security", moves[4]);
    assertEquals("Value: 1", moves[6]);
  }

  @Test
  public void testMoveToFirstSpotWithFirstIncreasesScoreProperlyWithMethodCalls() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 0, 0);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0), 0, 4);
    StringBuilder log = new StringBuilder();
    Sanguine transcript = new RecordModelMethodCallsMock(log, model);
    MoveValues vals = new GreedyMove().implementMove(transcript, Player.PLAYER1);
    String[] moves = log.toString().split(System.lineSeparator());
    assertEquals("getInputAt: Row = 0 Columns = 1", moves[14]);
    assertEquals("getHand: Player = Player 1", moves[0]);
    assertEquals("isValidMove: Card = Security", moves[4]);
    assertEquals("Value: 1", moves[6]);
  }

  @Test
  public void testtestMoveToFirstSpotWithGreedyIncreasesScoreProperlyWithMethodCalls() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 0, 0);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0), 0, 4);
    StringBuilder log = new StringBuilder();
    Sanguine transcript = new RecordModelMethodCallsMock(log, model);
    MoveValues vals = new FirstMove().implementMove(transcript, Player.PLAYER1);
    String[] moves = log.toString().split(System.lineSeparator());
    assertEquals("getInputAt: Row = 0 Columns = 1", moves[14]);
    assertEquals("getHand: Player = Player 1", moves[0]);
    assertEquals("isValidMove: Card = Security", moves[4]);
    assertEquals("Value: 1", moves[6]);
  }

  @Test
  public void testHowStrategyInteractsWithTheOnlyValidMoveBeingZeroZero() {
    StringBuilder log = new StringBuilder();
    Sanguine transcript = new SanguineOnlyAllowsMoveToRowZeroColZeroMock(log, model);
    transcript.startGame(3, 5, redDeck, blueDeck);
    MoveValues vals = new GreedyMove().implementMove(transcript, Player.PLAYER1);
    assertEquals(0, vals.getRow());
    assertEquals(0, vals.getCol());
  }

  @Test
  public void testHowStrategyInteractsWithTheOnlyValidMoveBeingTwoThree() {
    StringBuilder log = new StringBuilder();
    Sanguine mock = new SanguineOnlyAllowsMovesToRowTwoColThree(log, model);
    mock.startGame(3, 5, redDeck, blueDeck);
    MoveValues vals = new GreedyMove().implementMove(mock, Player.PLAYER1);
    assertEquals(2, vals.getRow());
    assertEquals(3, vals.getCol());
  }
}
