package sanguine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;

/**
 * represents the tests for our model of Sanguine.
 */
public class SanguineModelTest {

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


  //test to make sure that the game will throw throw another illegal state exception if we start
  //the game twice
  @Test (expected = IllegalStateException.class)
  public void testSanguineModelGameStartedTwiceThrowsError() {
    model.startGame(6, 7, redDeck, blueDeck);
    model.startGame(6, 7, redDeck, blueDeck);
  }

  //these next 3 tests will test with various VALID rows and columns to make sure the board
  //is properly instatiated with the correct values.
  @Test
  public void testSanguineModelGameStartedRowAndCol1() {
    model.startGame(6, 7, redDeck, blueDeck);
    assertEquals(6, model.getRows());
    assertEquals(7, model.getCols());
  }

  @Test
  public void testSanguineModelGameStartedRowAndCol2() {
    model.startGame(64, 77, redDeck, blueDeck);
    assertEquals(64, model.getRows());
    assertEquals(77, model.getCols());
  }

  @Test
  public void testSanguineModelGameStartedRowAndCol3() {
    model.startGame(3, 5, redDeck, blueDeck);
    assertEquals(3, model.getRows());
    assertEquals(5, model.getCols());
  }

  //we start with 15 in our deck so we want 5 in our hand after starting the game
  @Test
  public void testSanguineModelGameStartedHandSizeIsProper() {
    model.startGame(6, 7, redDeck, blueDeck);
    assertEquals(5, model.getHandSize(Player.PLAYER1));
    assertEquals(5, model.getHandSize(Player.PLAYER2));
  }

  //we want to now check invalid inputs for each individual test
  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGameStartedColIsNotOdd() {
    model.startGame(6, 8, redDeck, blueDeck);
  }

  //check to see if we have an invalid amount of rows cuz its negative or 0
  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGameStartedRowIsZero() {
    model.startGame(0, 7, redDeck, blueDeck);
  }

  //check to see if we have an invalid amount of rows cuz its negative or 0
  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGameStartedColIsZero() {
    model.startGame(6, 0, redDeck, blueDeck);
  }

  //check to see if we have an invalid amount of rows cuz its negative or 0
  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGameStartedRowIsNegative() {
    model.startGame(-1, 7, redDeck, blueDeck);
  }

  //check to see if we have an invalid amount of rows cuz its negative or 0
  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGameStartedColIsNegative() {
    model.startGame(6, -1, redDeck, blueDeck);
  }

  //test to make sure the board is properly instantiated with all that shi
  @Test
  public void testSanguineModelGameValidBoard() {
    model.startGame(3, 5, redDeck, blueDeck);
    //check all of the first col and then the last col have ONE PAWN and everything else has nothin
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        if (col == 0) {
          assertEquals(1, model.getInputAt(row, col).getPawns().getValue());
          assertEquals(Player.PLAYER1, model.getInputAt(row, col).getPlayer());
          continue;
        }
        if (col == 4) {
          assertEquals(1, model.getInputAt(row, col).getPawns().getValue());
          assertEquals(Player.PLAYER2, model.getInputAt(row, col).getPlayer());
          continue;
        }
        assertNull(model.getInputAt(row, col));
      }
    }
  }

  //test to make sure it also works with different rows and cols
  //test to make sure the board is properly instantiated with all that shi
  @Test
  public void testSanguineModelGameValidBoardDiffRowAndCol() {
    model.startGame(6, 7, redDeck, blueDeck);
    //check all of the first col and then the last col have ONE PAWN and everything else has nothin
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 7; col++) {
        if (col == 0) {
          assertEquals(1, model.getInputAt(row, col).getPawns().getValue());
          assertEquals(Player.PLAYER1, model.getInputAt(row, col).getPlayer());
          continue;
        }
        if (col == 6) {
          assertEquals(1, model.getInputAt(row, col).getPawns().getValue());
          assertEquals(Player.PLAYER2, model.getInputAt(row, col).getPlayer());
          continue;
        }
        assertNull(model.getInputAt(row, col));
      }
    }
  }

  @Test (expected = IllegalStateException.class)
  public void testSanguineModelGamePlaceCardGameNotStarted() {
    model.placeCard(redDeck.getFirst(), 1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGamePlaceCardInvalidRowAndColNegRow() {
    model.startGame(6, 7, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), -1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGamePlaceCardInvalidRowAndColNegCol() {
    model.startGame(6, 7, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGamePlaceCardInvalidRowAndColLargeRow() {
    model.startGame(6, 7, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 14, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSanguineModelGamePlaceCardInvalidRowAndColLargeCol() {
    model.startGame(6, 7, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 67);
  }

  @Test
  public void testSanguineModelGamePlaceCardOnCellWithNoPawns() {
    model.startGame(6, 7, redDeck, blueDeck);
    {
      IllegalStateException exception = assertThrows(
          IllegalStateException.class,
          () -> model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 2)
      );
      assertEquals("Invalid position to place a card, no pawns", exception.getMessage());
    }
  }

  @Test
  public void testSanguineModelGamePlaceCardOnCellWithNotEnoughPawns() {
    model.startGame(3, 5, redDeck, blueDeck);
    {
      IllegalStateException exception = assertThrows(
          IllegalStateException.class,
          () -> model.placeCard(redDeck.getLast(), 0, 0)
      );
      assertEquals("Invalid move",
          exception.getMessage());
    }
  }

  @Test
  public void testSanguineModelGamePlaceCardWhichOverwritesOtherPlayersPawns() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1); //p1
    assertEquals(Player.PLAYER1, model.getInputAt(1, 2).getPlayer()); //p1 owns
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 3); //p2
    //player 2 should own the pawn now
    assertEquals(Player.PLAYER2, model.getInputAt(1, 2).getPlayer()); //p2 owns
  }

  @Test
  public void testSanguineModelGamePlaceCardValidPlayer1() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    //test all of the relevant things have an extra pawn and the place has a card now
    assertEquals(Player.PLAYER1, model.getInputAt(1, 0).getPlayer());
    assertEquals(model.getHand(Player.PLAYER1).getFirst(), model.getInputAt(1, 0).getCard());
    assertNull(model.getInputAt(1, 0).getPawns()); //check to make sure pawns are now null
    assertEquals(2, model.getInputAt(2, 0).getPawns().getValue());
    assertEquals(2, model.getInputAt(0, 0).getPawns().getValue());
    assertEquals(1, model.getInputAt(1, 1).getPawns().getValue());
  }

  //does this by doing p1's move then does p2s move after
  @Test
  public void testSanguineModelGamePlaceCardValidPlayer2() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4);
    //test all of the relevant things have an extra pawn and the place has a card now
    assertEquals(Player.PLAYER2, model.getInputAt(1, 4).getPlayer());
    assertEquals(model.getHand(Player.PLAYER1).getFirst(),
        model.getInputAt(1, 4).getCard());
    assertNull(model.getInputAt(1, 4).getPawns()); //check to make sure pawns are now null
    assertEquals(2, model.getInputAt(2, 4).getPawns().getValue());
    assertEquals(2, model.getInputAt(0, 4).getPawns().getValue());
    assertEquals(1, model.getInputAt(1, 1).getPawns().getValue());
  }

  //check to make sure that we CANNOT move a card onto another players pawns / another players
  //cards
  @Test (expected = IllegalStateException.class)
  public void testSanguineModelGamePlaceCardOnInvalidPawnsP1() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 4);
  }

  //will test to make sure it works the other way around as well
  //check to make sure that we CANNOT move a card onto another players pawns / another players
  //cards
  @Test (expected = IllegalStateException.class)
  public void testSanguineModelGamePlaceCardOnInvalidPawnsP2() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 0);
  }

  //test to make sure that we dont place a card on another card
  //check to make sure that we CANNOT move a card onto another players pawns / another players
  //cards
  @Test (expected = IllegalStateException.class)
  public void testSanguineModelGamePlaceCardOnInvalidPawns() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
  }

  //test to make sure that we have our passmove and other moves increasing the player hand size by
  //1 and stuff since thats also part of the game
  @Test
  public void testSizeOfHandAfterPassMove() {
    model.startGame(3, 5, redDeck, blueDeck);
    int old = model.getHandSize(Player.PLAYER1);
    model.passMove();
    model.passMove();
    //The reason we want it to be the old val is because we are USING a card, then adding another
    //so it should be the same size, if we pass it should increase by 1.
    assertEquals(old + 1, model.getHandSize(Player.PLAYER1));
  }

  @Test
  public void testSizeOfHandAfterPlaceCard() {
    model.startGame(3, 5, redDeck, blueDeck);
    int old = model.getHandSize(Player.PLAYER1);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    //The reason we want it to be the old val is because we are USING a card, then adding another
    //so it should be the same size, if we pass it should increase by 1.
    assertEquals(old, model.getHandSize(Player.PLAYER1));
  }

  @Test
  public void testSizeOfHandAfterInvalidMove() {
    model.startGame(3, 5, redDeck, blueDeck);
    try {
      model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 4);
    } catch (IllegalStateException ignored) {
      assertEquals(5, model.getHandSize(Player.PLAYER1));
    }
  }

  //this test is funny, testing after passing 10 times we literally dont have cards left
  //and will just add nothing.
  @Test
  public void testSizeOfHandAfterPassingOneMillionFuckingTimes() {
    model.startGame(3, 5, redDeck, blueDeck);
    int oldP1 = model.getHandSize(Player.PLAYER1);
    int oldP2 = model.getHandSize(Player.PLAYER2);
    for (int passes = 0; passes <= 10; passes++) {
      model.passMove(); //p1
      model.passMove(); //p2
    }
    //check to make sure that the current value isnt the same as the old one yk
    assertNotEquals(oldP1, model.getHandSize(Player.PLAYER1));
    assertNotEquals(oldP2, model.getHandSize(Player.PLAYER2));

    assertEquals(15, model.getHandSize(Player.PLAYER1));
    assertEquals(15, model.getHandSize(Player.PLAYER2));
    //tests to make sure the size of the deck is still the same even after!
    model.passMove(); //p1
    model.passMove(); //p2
    assertEquals(15, model.getHandSize(Player.PLAYER1));
    assertEquals(15, model.getHandSize(Player.PLAYER2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRowScoreInvalidRowTooSmall() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.rowScore(-1, Player.PLAYER1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRowScoreInvalidRowTooLarge() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.rowScore(7, Player.PLAYER1);
  }

  @Test
  public void testRowScoreValidWithEmptyNoCardRow() {
    model.startGame(3, 5, redDeck, blueDeck);
    assertEquals(0, model.rowScore(0, Player.PLAYER1));
  }

  @Test
  public void testRowScoreValidWithEmptyCardWithOnly1Card() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    assertEquals(1, model.rowScore(1, Player.PLAYER1));
  }

  @Test
  public void testRowScoreValidWithEmptyCardWithMultipleCards() {
    model.startGame(3, 5, redDeck, blueDeck);
    //assertEquals("",model.getHand(Player.PLAYER1).toString());
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1);
    assertEquals(2, model.rowScore(1, Player.PLAYER1));
  }

  @Test
  public void testTotalScoreWithCardInEveryRowForOnePlayer() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 0);
    //total score is 4 for p1
    assertEquals(4, model.totalScore(Player.PLAYER1));
  }

  @Test
  public void testTotalScoreWithCardInEveryRowForBothPlayers() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 0); //p1
    //total score is 2 as neither gains points for stuff
    assertEquals(2, model.totalScore(Player.PLAYER1));
  }

  @Test
  public void testTotalScoreWithCardInEveryRowForBothPlayersNoWonRowsIsZero() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 0); //p1
    //total score is 0 as P2 won NO ROWS
    assertEquals(0, model.totalScore(Player.PLAYER2));
  }

  @Test (expected = IllegalStateException.class)
  public void testGameOverWithGameNotStartedThrowsException() {
    model.isGameOver();
  }

  @Test
  public void testGameOverWithTwoConsecutivePasses() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.passMove();
    model.passMove();
    assertTrue(model.isGameOver());
  }

  @Test
  public void testGameIsNotOverWithTwoNonConsecutivePasses() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.passMove();
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 4);
    model.passMove();
    assertFalse(model.isGameOver());
  }

  @Test (expected = IllegalStateException.class)
  public void testWhoWonGameNotStarted() {
    model.whoWon();
  }

  @Test (expected = IllegalStateException.class)
  public void testWhoWonGameNotOver() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 0); //p1
    model.whoWon();
  }

  @Test
  public void testWhoWonGameOverPlayer1() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 1); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 0); //p1
    model.passMove();
    model.passMove();
    assertEquals(Player.PLAYER1, model.whoWon());
  }

  @Test
  public void testWhoWonGameOverPlayer2() {
    model.startGame(3, 5, redDeck, blueDeck);
    model.passMove(); //pass to allow p2 to start because i didn't want to rewrite it
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 1, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 1, 3); //p2
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 0, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 0, 4); //p2
    model.placeCard(model.getHand(Player.PLAYER2).getFirst(), 2, 0); //p1
    model.placeCard(model.getHand(Player.PLAYER1).getFirst(), 2, 4); //p2
    model.passMove();
    model.passMove();
    assertEquals(Player.PLAYER2, model.whoWon());
  }

  @Test
  public void testGetRow1() {
    model.startGame(3, 5, redDeck, blueDeck);
    assertEquals(3, model.getRows());
  }

  @Test
  public void testGetRow2() {
    model.startGame(6, 5, redDeck, blueDeck);
    assertEquals(6, model.getRows());
  }

  @Test
  public void testGetRow3() {
    model.startGame(67, 5, redDeck, blueDeck);
    assertEquals(67, model.getRows());
  }

  @Test
  public void testGetCol1() {
    model.startGame(3, 41, redDeck, blueDeck);
    assertEquals(41, model.getCols());
  }

  @Test
  public void testGetCol2() {
    model.startGame(6, 13, redDeck, blueDeck);
    assertEquals(13, model.getCols());
  }

  @Test
  public void testGetCol3() {
    model.startGame(67, 5, redDeck, blueDeck);
    assertEquals(5, model.getCols());
  }

  @Test
  public void testThatPawnDoesNotOverwriteCard() {
    sanguine.model.Sanguine model = new BasicSanguine();
    List<Card> redDeck = new DeckCreatorImpl().createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> blueDeck = new DeckCreatorImpl().createDeck(Player.PLAYER2, "config/red.deck");
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
    //this move will have influence that should NOT override the card at 0, 3
    model.placeCard(model.getHand(Player.PLAYER1).get(1), 0, 2);
    assertNotEquals(null, model.getInputAt(0, 3).getCard());
  }
}
