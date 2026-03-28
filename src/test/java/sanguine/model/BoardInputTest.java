package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;
import sanguine.model.card.Card;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.BoardInputsPawns;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreatorImpl;

/**
 * This class represents tests for the implementation of the BoardInput class. Intention is to
 * ensure code reliability and to ensure intended behavior.
 */
public class BoardInputTest {

  @Test
  public void testGettersFromBoardInput() {
    BoardInput.BoardInputBuilder builder = new BoardInput.BoardInputBuilder();
    List<Card> deck = new DeckCreatorImpl().createDeck(Player.PLAYER1, "config/red.deck");
    BoardInput boardInputCard = builder.setPlayer(Player.PLAYER1).setCard(deck.getFirst()).build();
    assertEquals(Player.PLAYER1, boardInputCard.getPlayer());
    assertEquals(deck.getFirst(), boardInputCard.getCard());
    BoardInput boardInputPawn = builder.setPlayer(Player.PLAYER2)
        .setPawns(BoardInputsPawns.ONE).build();
    assertEquals(BoardInputsPawns.ONE, boardInputPawn.getPawns());
  }

  @Test
  public void testBuilderUsingNullAsArguments() {
    BoardInput.BoardInputBuilder builder = new BoardInput.BoardInputBuilder();
    BoardInput boardInputCard = builder.setPlayer(null).setCard(null).build();
    assertNull(boardInputCard.getPlayer());
    assertNull(boardInputCard.getCard());
    BoardInput boardInputPawn = builder.setPlayer(null).setPawns(null).build();
    assertNull(boardInputPawn.getPawns());
  }

  @Test
  public void testAddPawnMethodFromBoardInput() {
    BoardInput.BoardInputBuilder builder = new BoardInput.BoardInputBuilder();
    BoardInput boardInput = builder.build();
    assertNull(boardInput.getPawns());
    boardInput.addPawn();
    assertEquals(BoardInputsPawns.ONE, boardInput.getPawns());
    boardInput.addPawn();
    assertEquals(BoardInputsPawns.TWO, boardInput.getPawns());
    boardInput.addPawn();
    assertEquals(BoardInputsPawns.THREE, boardInput.getPawns());
  }

  @Test
  public void testAddCardMethodFromBoardInput() {
    BoardInput.BoardInputBuilder builder = new BoardInput.BoardInputBuilder();
    BoardInput boardInput = builder.setPawns(BoardInputsPawns.ONE).build();
    assertEquals(BoardInputsPawns.ONE, boardInput.getPawns());
    List<Card> deck = new DeckCreatorImpl().createDeck(Player.PLAYER1, "config/red.deck");
    boardInput.addCard(deck.getFirst());
    assertNull(boardInput.getPawns());
    assertEquals(deck.getFirst(), boardInput.getCard());
  }
}
