package sanguine.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;

/**
 * general tests for the textual view for sanguine.
 */
public class SanguineTextualViewTest {

  @Test
  public void testToStringToCheckCorrectness() {
    Sanguine model = new BasicSanguine();
    DeckCreator create = new DeckCreatorImpl();
    List<Card> redDeck = create.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> blueDeck = create.createDeck(Player.PLAYER2, "config/blue.deck");
    model.startGame(3, 5, redDeck, blueDeck);
    SanguineTextualView view = new SanguineTextualViewImpl(model);
    assertEquals("0 1___1 0" + System.lineSeparator()
        + "0 1___1 0" + System.lineSeparator()
        + "0 1___1 0" + System.lineSeparator(), view.toString());
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4);
    assertEquals("0 2___2 0" + System.lineSeparator()
        + "1 R1_1B 1" +  System.lineSeparator()
        + "0 2___2 0" + System.lineSeparator(), view.toString());
  }

  @Test
  public void testConstructorWithInvalidArguments() {
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new SanguineTextualViewImpl(null)
      );
      assertEquals("Model cannot be null", exception.getMessage());
    }
  }
}
