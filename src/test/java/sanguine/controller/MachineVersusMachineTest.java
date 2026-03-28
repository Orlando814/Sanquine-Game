package sanguine.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import sanguine.controller.mocks.SanguineControllerMockIntegrationTest;
import sanguine.controller.mocks.SanguineGuiFrameMock;
import sanguine.model.BasicSanguine;
import sanguine.model.ReadOnlyBasicSanguine;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.strategy.GreedyMove;
import sanguine.view.SanguineGuiView;

/**
 * Represents an integration test where two machine players run against each other. Point is to
 * verify that implementation works when actual players play with the actual MVC components. Some
 * of the MVC components are mocks, but they have identical implementation as the real ones except
 * they keep any visuals from being visible to the user so the test runs properly
 */
public class MachineVersusMachineTest {

  @Test
  public void testMachineVersusMachineIntegrationTest() {
    Sanguine model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, "config/blue.deck");
    ReadOnlySanguine readOnlyModel = new ReadOnlyBasicSanguine(model);
    PlayerAction humanP1 = new PlayerImpl(Player.PLAYER1);
    PlayerAction greedyP2 = new MachineImpl(Player.PLAYER2, new GreedyMove());
    // Mocks are here to keep view from appearing when test is run.
    SanguineGuiView viewP1 = new SanguineGuiFrameMock(readOnlyModel, humanP1);
    SanguineGuiView viewP2 = new SanguineGuiFrameMock(readOnlyModel, greedyP2);
    // Mocks are here to keep invalid move or game over message from appearing when test is run.
    SanguineController controllerP1 = new SanguineControllerMockIntegrationTest(viewP1, humanP1,
        model);
    SanguineController controllerP2 = new SanguineControllerMockIntegrationTest(viewP2, greedyP2,
        model);
    controllerP1.playGame();
    controllerP2.playGame();
    model.startGame(5, 7, cardsPlayer1, cardsPlayer2);
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    ((ViewFeaturesListener) controllerP1).keyClicked("p");
    assertTrue(model.isGameOver()); // game ended
    assertEquals(Player.PLAYER2, model.whoWon()); // a player won
    assertEquals(0, model.totalScore(Player.PLAYER1)); // Player 1 has unique total score
    assertEquals(21, model.totalScore(Player.PLAYER2)); // Player 2 has unique total score
    assertNotEquals(5, model.getHandSize(Player.PLAYER1)); // Player 1 unique hand size
    assertNotEquals(5, model.getHandSize(Player.PLAYER2)); // Player 2 unique hand size
  }
}
