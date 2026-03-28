package sanguine.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.model.card.BasicCard;
import sanguine.model.card.CardExtras;
import sanguine.model.card.CardInfluence;
import sanguine.model.card.Cost;

/**
 * This class represents tests for the implementation of any classes that implement the CardExtras
 * interface. Intention is to ensure code reliability and to ensure intended behavior.
 */
public class CardExtrasTest {

  @Test
  public void testCardConstructorWithInvalidArguments() {
    String name = "yuh";
    Cost cost = Cost.ONE;
    List<List<CardInfluence>> goodList = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(null);
      }
      goodList.add(row);
    }
    List<List<CardInfluence>> badList = new ArrayList<>();
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new BasicCard(null, cost, 1, goodList)
      );
      assertEquals("Cost, influence, or name cannot be null", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new BasicCard(name, null, 1, goodList)
      );
      assertEquals("Cost, influence, or name cannot be null", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new BasicCard(name, cost, 1, null)
      );
      assertEquals("Cost, influence, or name cannot be null", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new BasicCard(name, cost, 0, goodList)
      );
      assertEquals("Value must be greater than 0", exception.getMessage());
    }
    {
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> new BasicCard(name, cost, 1, badList)
      );
      assertEquals("Influence array must be of size 25", exception.getMessage());
    }
  }

  @Test
  public void testCardGettersAndToString() {
    List<List<CardInfluence>> goodList = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(null);
      }
      goodList.add(row);
    }
    CardExtras card = new BasicCard("Yuh", Cost.ONE, 1, goodList);
    assertEquals("Yuh", card.getName());
    assertEquals(1, card.getCost());
    for (int  index = 0; index < goodList.size(); index++) {
      for (int index2 = 0; index2 < goodList.get(index).size(); index2++) {
        assertNull(card.getArray().get(index).get(index2));
      }
    }
    assertEquals("Yuh", card.getName().toString());
  }

  @Test
  public void testCardEquals() {
    List<List<CardInfluence>> goodList1 = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(null);
      }
      goodList1.add(row);
    }
    List<List<CardInfluence>> goodList2 = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(CardInfluence.INFLUENCE);
      }
      goodList2.add(row);
    }
    CardExtras cardYuh1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardYuh2 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    assertEquals(cardYuh1, cardYuh2);
    CardExtras cardBruh = new BasicCard("Bruh", Cost.ONE, 1, goodList1);
    assertNotEquals(cardYuh1, cardBruh);
    CardExtras cardCost1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardCost2 = new BasicCard("Yuh", Cost.TWO, 1, goodList1);
    assertNotEquals(cardCost1, cardCost2);
    CardExtras cardValue1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardValue2 = new BasicCard("Yuh", Cost.ONE, 2, goodList1);
    assertNotEquals(cardValue1, cardValue2);
    CardExtras cardList1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardList2 = new BasicCard("Yuh", Cost.ONE, 1, goodList2);
    assertNotEquals(cardList1, cardList2);
    assertNotEquals(1, cardList1);
    assertNotEquals("yuh", cardList1);
    assertNotEquals(Cost.ONE, cardList1);
  }

  @Test
  public void testCardHashCode() {
    List<List<CardInfluence>> goodList1 = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(null);
      }
      goodList1.add(row);
    }
    List<List<CardInfluence>> goodList2 = new ArrayList<>();
    for (int index = 0; index < 5; index++) {
      List<CardInfluence> row = new ArrayList<>();
      for (int index2 = 0; index2 < 5; index2++) {
        row.add(CardInfluence.INFLUENCE);
      }
      goodList2.add(row);
    }
    CardExtras cardYuh1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardYuh2 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    assertEquals(cardYuh1.hashCode(), cardYuh2.hashCode());
    CardExtras cardBruh = new BasicCard("Bruh", Cost.ONE, 1, goodList1);
    assertNotEquals(cardYuh1.hashCode(), cardBruh.hashCode());
    CardExtras cardCost1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardCost2 = new BasicCard("Yuh", Cost.TWO, 1, goodList1);
    assertNotEquals(cardCost1.hashCode(), cardCost2.hashCode());
    CardExtras cardValue1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardValue2 = new BasicCard("Yuh", Cost.TWO, 1, goodList1);
    assertNotEquals(cardValue1.hashCode(), cardValue2.hashCode());
    CardExtras cardList1 = new BasicCard("Yuh", Cost.ONE, 1, goodList1);
    CardExtras cardList2 = new BasicCard("Yuh", Cost.TWO, 1, goodList2);
    assertNotEquals(cardList1.hashCode(), cardList2.hashCode());
  }
}
