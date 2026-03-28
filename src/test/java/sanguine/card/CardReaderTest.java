package sanguine.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import sanguine.card.mocks.CardReaderAlwaysThrowsIoExceptionMock;
import sanguine.model.card.CardInfluence;
import sanguine.model.card.CardReader;
import sanguine.model.card.ReadCardFile;

/**
 * This class represents tests for the implementation of any classes that implement the CardReader
 * interface. Intention is to ensure code reliability and to ensure intended behavior.
 */
public class CardReaderTest {
  @Test
  public void testInvalidArgumentInputsForClassInitialization() {
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new ReadCardFile(null)
      );
      assertEquals("File cannot be null", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new ReadCardFile(new File("bruh"))
      );
      assertEquals("File cannot be found", exception.getMessage());
    }
  }

  @Test
  public void testIfListOfCardsIsCreatedCorrectlyUsingShowCardsMethod() {
    File deckConfig = new File("config" + File.separator + "red.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    List<String> cards = cardReader.showCards();
    assertEquals("Security 1 1" + System.lineSeparator()
        + "XXXXX" + System.lineSeparator()
        + "XXIXX" +  System.lineSeparator()
        + "XICIX" +  System.lineSeparator()
        + "XXIXX" + System.lineSeparator()
        + "XXXXX", cards.getFirst());
    assertEquals("Wheel 1 1" + System.lineSeparator()
        + "XXXXI" + System.lineSeparator()
        + "XXXXX" + System.lineSeparator()
        + "XXCXX" + System.lineSeparator()
        + "XXXXX" + System.lineSeparator()
        + "XXXXI", cards.get(5));
    assertEquals("Rider 3 5" + System.lineSeparator()
        + "XXXXX" + System.lineSeparator()
        + "XXXXX" + System.lineSeparator()
        + "XXCIX" + System.lineSeparator()
        + "XIIIX" + System.lineSeparator()
        + "XXXXX", cards.getLast());
  }

  @Test
  public void testShowNameWithValidArgumentsOfTypeName() {
    File deckConfig = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    assertEquals("Security", cardReader.showNameValueCost(0, "name"));
    assertEquals("Crab", cardReader.showNameValueCost(4, "name"));
    assertEquals("Flame", cardReader.showNameValueCost(7, "name"));
    assertEquals("Quetz", cardReader.showNameValueCost(12, "name"));
    assertEquals("Rider", cardReader.showNameValueCost(14, "name"));
  }

  @Test
  public void testShowNameWithValidArgumentsOfTypeCost() {
    File deckConfig = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    assertEquals("1", cardReader.showNameValueCost(0, "cost"));
    assertEquals("1", cardReader.showNameValueCost(4, "cost"));
    assertEquals("1", cardReader.showNameValueCost(7, "cost"));
    assertEquals("2", cardReader.showNameValueCost(12, "cost"));
    assertEquals("3", cardReader.showNameValueCost(14, "cost"));
  }

  @Test
  public void testShowNameWithValidArgumentsOfTypeValue() {
    File deckConfig = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    assertEquals("1", cardReader.showNameValueCost(0, "value"));
    assertEquals("1", cardReader.showNameValueCost(4, "value"));
    assertEquals("3", cardReader.showNameValueCost(7, "value"));
    assertEquals("3", cardReader.showNameValueCost(12, "value"));
    assertEquals("5", cardReader.showNameValueCost(14, "value"));
  }

  @Test
  public void testShowNameWithInvalidArguments() {
    File deckConfig = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader.showNameValueCost(-1, "name")
      );
      assertEquals("Card index must be between 0 and 15", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader.showNameValueCost(15, "name")
      );
      assertEquals("Card index must be between 0 and 15", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader.showNameValueCost(4, "wdw")
      );
      assertEquals("type must be: name, cost, or value", exception.getMessage());
    }
  }

  @Test
  public void testShowInfluenceWithInvalidArguments() {
    File deckConfig = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader.showInfluence(-1)
      );
      assertEquals("Card index must be between 0 and 15", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader.showInfluence(15)
      );
      assertEquals("Card index must be between 0 and 15", exception.getMessage());
    }
    File deckConfig1 = new File("config" + File.separator + "testingExample.deck");
    CardReader cardReader1 = new ReadCardFile(deckConfig1);
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader1.showInfluence(0)
      );
      assertEquals("State of card influence is invalid", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader1.showInfluence(1)
      );
      assertEquals("State of card influence is invalid", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader1.showInfluence(2)
      );
      assertEquals("State of card influence is invalid", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> cardReader1.showInfluence(3)
      );
      assertEquals("State of card influence is invalid", exception.getMessage());
    }
  }

  @Test
  public void testShowInfluenceWithValidArguments() {
    File deckConfig = new File("config" + File.separator + "red.deck");
    CardReader cardReader = new ReadCardFile(deckConfig);
    CardInfluence card = CardInfluence.CARD;
    CardInfluence influence = CardInfluence.INFLUENCE;
    List<List<CardInfluence>> securityInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    assertEquals(securityInfluence, cardReader.showInfluence(0));

    List<List<CardInfluence>> queenInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, card, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null))));
    assertEquals(queenInfluence, cardReader.showInfluence(2));

    List<List<CardInfluence>> crabInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, influence, null, null)),
        new ArrayList<>(Arrays.asList(null, influence, card, influence, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null))));
    assertEquals(crabInfluence, cardReader.showInfluence(3));

    List<List<CardInfluence>> wheelInfluence = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(null, null, null, null, influence)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, card, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, null)),
        new ArrayList<>(Arrays.asList(null, null, null, null, influence))));
    assertEquals(wheelInfluence, cardReader.showInfluence(5));
  }

  @Test
  public void testThatCardReaderCanHandleIoException() {
    File deckConfig = new File("config" + File.separator + "red.deck");
    {
      IllegalStateException exception = assertThrows(
          IllegalStateException.class,
          () -> new CardReaderAlwaysThrowsIoExceptionMock(deckConfig)
      );
      assertEquals("Bad IO", exception.getMessage());
    }
  }
}
