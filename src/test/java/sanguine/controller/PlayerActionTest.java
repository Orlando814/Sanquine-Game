package sanguine.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.strategy.FirstMove;
import sanguine.strategy.GreedyMove;
import sanguine.view.SanguineGuiFrame;
import sanguine.view.SanguineGuiView;

/**
 * This holds all the respective test for any class that implements the PlayerAction interface.
 * This class's intent is to ensure correct behavior and code correctness.
 */
public class PlayerActionTest {

  @Test
  public void testGetPlayer() {
    PlayerAction humanPlayer1 = new PlayerImpl(Player.PLAYER1);
    assertEquals(Player.PLAYER1, humanPlayer1.getPlayer());
    PlayerAction humanPlayer2 = new PlayerImpl(Player.PLAYER2);
    assertEquals(Player.PLAYER2, humanPlayer2.getPlayer());
    PlayerAction machinePlayer1 = new MachineImpl(Player.PLAYER1, new FirstMove());
    assertEquals(Player.PLAYER1, machinePlayer1.getPlayer());
    PlayerAction machinePlayer2 = new MachineImpl(Player.PLAYER2, new FirstMove());
    assertEquals(Player.PLAYER2, machinePlayer2.getPlayer());
  }

  @Test
  public void testImplementMoveHuman() {
    PlayerAction humanPlayer1 = new PlayerImpl(Player.PLAYER1);
    ReadOnlySanguine model = new BasicSanguine();
    ReadOnlySanguine modelCopy = new BasicSanguine();
    humanPlayer1.makeMove(model);
    assertEquals(model.getHand(Player.PLAYER1), modelCopy.getHand(Player.PLAYER1));
    assertEquals(model.getHand(Player.PLAYER2), modelCopy.getHand(Player.PLAYER2));
  }

  @Test
  public void testImplementMoveMachine() {
    PlayerAction machinePlayer1 = new MachineImpl(Player.PLAYER2, new GreedyMove());
    Sanguine model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, "config/blue.deck");
    model.startGame(5, 7, cardsPlayer1, cardsPlayer2);
    SanguineGuiView view = new SanguineGuiFrame(model, machinePlayer1);
    ViewFeaturesListener cont = new BasicSanguineController(view, machinePlayer1, model);
    view.subscribe(cont);
    model.subscribe((ModelFeaturesListener) cont);
    machinePlayer1.subscribe((PlayerActionsListener) view);
    machinePlayer1.makeMove(model);
    Sanguine modelCopy = new BasicSanguine();
    modelCopy.startGame(5, 7, cardsPlayer1, cardsPlayer2);
    assertNotEquals(model.getHand(Player.PLAYER1), modelCopy.getHand(Player.PLAYER1));
    assertNotEquals(model.getHand(Player.PLAYER2), modelCopy.getHand(Player.PLAYER2));
  }

  @Test
  public void testSubscribeMachine() {
    PlayerAction machinePlayer1 = new MachineImpl(Player.PLAYER1, new GreedyMove());
    {
      NullPointerException exception = assertThrows(
          NullPointerException.class,
          () -> machinePlayer1.makeMove(new BasicSanguine())
      );
      assertEquals("Cannot invoke \"sanguine.controller.PlayerActionsListener."
              + "hasMoveBeenMade(sanguine.strategy.MoveValues)\" because \"this.listener\" is null",
          exception.getMessage());
    }
    Sanguine model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, "config/blue.deck");
    model.startGame(5, 7, cardsPlayer1, cardsPlayer2);
    SanguineGuiView view = new SanguineGuiFrame(model, machinePlayer1);
    ViewFeaturesListener cont = new BasicSanguineController(view, machinePlayer1, model);
    view.subscribe(cont);
    model.subscribe((ModelFeaturesListener) cont);
    machinePlayer1.subscribe((PlayerActionsListener) view);
    machinePlayer1.makeMove(model); // Does not error after subscribe method has been used
  }

  @Test
  public void testSubscribeHuman() {
    PlayerAction humanPlayer1 = new PlayerImpl(Player.PLAYER1);
    humanPlayer1.makeMove(new BasicSanguine()); //Runs because method doesn't do anything
    Sanguine model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, "config/blue.deck");
    model.startGame(5, 7, cardsPlayer1, cardsPlayer2);
    SanguineGuiView view = new SanguineGuiFrame(model, humanPlayer1);
    ViewFeaturesListener cont = new BasicSanguineController(view, humanPlayer1, model);
    view.subscribe(cont);
    model.subscribe((ModelFeaturesListener) cont);
    humanPlayer1.subscribe((PlayerActionsListener) view);
    humanPlayer1.makeMove(model); //Runs after MVC is linked because method doesn't do anything
  }
}
