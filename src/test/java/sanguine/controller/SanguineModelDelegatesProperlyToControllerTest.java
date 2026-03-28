package sanguine.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.mocks.SanguineControllerMock;
import sanguine.model.BasicSanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;

/**
 * will test to make sure that the sanguine model properly delegates the correct
 * info. This means that the sanguine model will send the info and our controllers log will store
 * it for us to test with.
 */
public class SanguineModelDelegatesProperlyToControllerTest {

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
  public void testThatChangeInPlayerNotifiesListeners() {
    Appendable log = new StringBuilder();
    SanguineController mock = new SanguineControllerMock(log);
    model.subscribe((ModelFeaturesListener) mock);
    model.startGame(5, 7, redDeck, blueDeck);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0),
        0, 0);
    model.placeCard(model.getCardInHand(Player.PLAYER2, 0),
        0, 6);
    model.placeCard(model.getCardInHand(Player.PLAYER1, 0), 1, 0);
    model.passMove();
    model.passMove();
    String output = log.toString();
    String[] transcript = output.split("\n");
    assertEquals("Called whose turn with Player: Player 1", transcript[0]); //start game
    assertEquals("Called whose turn with Player: Player 2", transcript[1]); //place
    assertEquals("Called whose turn with Player: Player 1", transcript[2]); //p;ace
    assertEquals("Called whose turn with Player: Player 2", transcript[3]); //place
    assertEquals("Called whose turn with Player: Player 1", transcript[4]); //pass
    assertEquals("Called whose turn with Player: Player 2", transcript[5]); //pass
    assertEquals("Called gameOver with player: Player 1", transcript[6]); //game over
  }
}
