package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.card.CardInfluence;
import sanguine.model.card.Cost;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;


/**
 * This class represents tests for the implementation of any classes that implement the DeckCreator
 * interface. Intention is to ensure code reliability and to ensure intended behavior.
 */
public class DeckCreatorTest {

  @Test
  public void testGetCostWithValidInputs() {
    assertEquals(Cost.ONE, Cost.getCost(1));
    assertEquals(Cost.TWO, Cost.getCost(2));
    assertEquals(Cost.THREE, Cost.getCost(3));
  }

  @Test
  public void testGetCostWithInvalidInputs() {
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> Cost.getCost(-1)
      );
      assertEquals("Cost must be either 1, 2, or 3", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> Cost.getCost(4)
      );
      assertEquals("Cost must be either 1, 2, or 3", exception.getMessage());
    }
  }

  @Test
  public void testCreateDeckByCheckingItReturnsCorrectRedDeck() {
    DeckCreator create = new DeckCreatorImpl();
    List<Card> redDeck = create.createDeck(Player.PLAYER1, "config/red.deck");
    assertEquals(15, redDeck.size());
    CardInfluence card = CardInfluence.CARD;
    CardInfluence influence = CardInfluence.INFLUENCE;
    List<List<CardInfluence>> securityInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card security = new BasicCard("Security", Cost.ONE, 1, securityInfluence);
    assertEquals(security, redDeck.getFirst());

    List<List<CardInfluence>> riderInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, influence, influence, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card rider = new BasicCard("Rider", Cost.THREE, 5, riderInfluence);
    assertEquals(rider, redDeck.getLast());

    List<List<CardInfluence>> crabInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card crab = new BasicCard("Crab", Cost.ONE, 1, crabInfluence);
    assertEquals(crab, redDeck.get(3));

    List<List<CardInfluence>> bigInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, null, influence, null)),
        new ArrayList<>(Arrays.asList(influence, null, card, null, influence)),
        new ArrayList<>(Arrays.asList(null, influence, null, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null))));
    Card big = new BasicCard("Big", Cost.THREE, 5, bigInfluence);
    assertEquals(big, redDeck.get(13));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateDeckWithInvalidAmountOfCards() {
    DeckCreator create = new DeckCreatorImpl();
    List<Card> blueDeck = create.createDeck(Player.PLAYER2, "config/fuckedup.deck");
  }

  @Test
  public void testCreateDeckByCheckingItReturnsCorrectBlueDeck() {
    DeckCreator create = new DeckCreatorImpl();
    List<Card> blueDeck = create.createDeck(Player.PLAYER2, "config/red.deck");
    assertEquals(15, blueDeck.size());
    CardInfluence card = CardInfluence.CARD;
    CardInfluence influence = CardInfluence.INFLUENCE;
    List<List<CardInfluence>> securityInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card security = new BasicCard("Security", Cost.ONE, 1, securityInfluence);
    assertEquals(security, blueDeck.getFirst());

    List<List<CardInfluence>> riderInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, influence, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card rider = new BasicCard("Rider", Cost.THREE, 5, riderInfluence);
    assertEquals(rider, blueDeck.getLast());

    List<List<CardInfluence>> crabInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    Card crab = new BasicCard("Crab", Cost.ONE, 1, crabInfluence);
    assertEquals(crab, blueDeck.get(3));

    List<List<CardInfluence>> bigInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, null, influence, null)),
        new ArrayList<>(Arrays.asList(influence, null, card, null, influence)),
        new ArrayList<>(Arrays.asList(null, influence, null, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null))));
    Card big = new BasicCard("Big", Cost.THREE, 5, bigInfluence);
    assertEquals(big, blueDeck.get(13));

    List<List<CardInfluence>> wheelInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(influence, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, card, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(influence, null, null, null, null))));
    Card wheel = new BasicCard("Wheel", Cost.ONE,  1, wheelInfluence);
    assertEquals(wheel, blueDeck.get(5));
  }
}
