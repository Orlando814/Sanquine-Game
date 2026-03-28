package sanguine.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.mocks.SanguineControllerMock;
import sanguine.model.BasicSanguine;
import sanguine.model.ReadOnlyBasicSanguine;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.strategy.GreedyMove;
import sanguine.view.SanguineGuiFrame;
import sanguine.view.SanguineGuiView;

/**
 * represents our class which properly delegates the controller.
 */
public class SanguinePlayerDelegatesProperlyToControllerTest {

  Sanguine model = new BasicSanguine();
  List<Card> redDeck = new ArrayList<>();
  List<Card> blueDeck = new ArrayList<>();
  ReadOnlySanguine readOnlyModel = new ReadOnlyBasicSanguine(model);

  //vars to setup (just a basic normal game that works and I will mutate it later after each init)
  void init() {
    //model to use for testing’s sake
    model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    redDeck = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    blueDeck = createDeck.createDeck(Player.PLAYER2, "config/red.deck");
    readOnlyModel = new ReadOnlyBasicSanguine(model);
  }

  /**
   * setup :D.
   */
  @Before
  public void setUp() {
    init();
  }


  @Test
  public void testThatPlayerDelegatesProperlyToControllerMachinePlayer() {
    PlayerAction greedyP1 = new MachineImpl(Player.PLAYER1, new GreedyMove());
    PlayerAction greedyP2 = new MachineImpl(Player.PLAYER2, new GreedyMove());
    SanguineGuiView viewP1 = new SanguineGuiFrame(readOnlyModel, greedyP1);
    SanguineGuiView viewP2 = new SanguineGuiFrame(readOnlyModel, greedyP2);
    Appendable logP1 = new StringBuilder();
    Appendable logP2 = new StringBuilder();
    SanguineController mockP1 = new SanguineControllerMock(logP1);
    SanguineController mockP2 = new SanguineControllerMock(logP2);
    viewP1.subscribe((ViewFeaturesListener) mockP1);
    greedyP1.subscribe((PlayerActionsListener) viewP1);
    viewP2.subscribe((ViewFeaturesListener) mockP2);
    greedyP2.subscribe((PlayerActionsListener) viewP2);
    model.startGame(5, 7, redDeck, blueDeck);
    //mock to make sure we get the right move then actually do the move
    greedyP1.makeMove(readOnlyModel);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 0, 0);
    //mock to make sure we ge the right move then actually do the move
    greedyP2.makeMove(readOnlyModel);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0), 0, 6);
    //mock to make sure we get the right move then actually do the move
    greedyP1.makeMove(readOnlyModel);
    //mock to make sure we ge the right move then actually do the move
    greedyP2.makeMove(readOnlyModel);
    String outputP1 = logP1.toString();
    String[] transcriptP1 = outputP1.split("\n");
    String outputP2 = logP2.toString();
    final String[] transcriptP2 = outputP2.split("\n"); //final b/c checkstyle
    //PLAYER 1 MOVE 1
    assertEquals("Called mouseEventBoard with X: 0 Y: 1", transcriptP1[0]);
    assertEquals("Called mouseEventHand with cardIndex: 0 and player: Player 1",
        transcriptP1[1]);
    assertEquals("Called keyClicked with key: m", transcriptP1[2]);

    //PLAYER 2 MOVE 2
    assertEquals("Called mouseEventBoard with X: 0 Y: 7", transcriptP2[0]);
    assertEquals("Called mouseEventHand with cardIndex: 0 and player: Player 2",
        transcriptP2[1]);
    assertEquals("Called keyClicked with key: m", transcriptP2[2]);

    //PLAYER 1 MOVE 2
    assertEquals("Called mouseEventBoard with X: 0 Y: 2", transcriptP1[3]);
    assertEquals("Called mouseEventHand with cardIndex: 0 and player: Player 1",
        transcriptP1[4]);
    assertEquals("Called keyClicked with key: m", transcriptP1[5]);

    //PLAYER 2 MOVE 2
    assertEquals("Called mouseEventBoard with X: 0 Y: 6", transcriptP2[3]);
    assertEquals("Called mouseEventHand with cardIndex: 0 and player: Player 2",
        transcriptP2[4]);
    assertEquals("Called keyClicked with key: m", transcriptP2[5]);
  }
}
