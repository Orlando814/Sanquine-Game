package sanguine.model.cell;

import sanguine.model.card.Card;

/**
 * represents the actual input that a board can have within it. Will also associate that input
 * with the player that it is with. This will allow us to have the player associated with its
 * items / cards / pawns.
 */
public class BoardInput {

  //the pawn enum that can be associated with the input
  private BoardInputsPawns pawns;

  //the card that could be associated with the given spot on the board
  private Card card;

  //the player that is associated with this given space
  private final Player player;

  private BoardInput(BoardInputsPawns pawns, Card card, Player player) {
    this.pawns = pawns;
    this.card = card;
    this.player = player;
  }

  /**
   * will get the pawns on a given BoardInput object.
   *
   * @return the object itself, this is a getter.
   */
  public BoardInputsPawns getPawns() {
    return this.pawns;
  }

  /**
   * gets the card at the given BoardInput.
   *
   * @return the card at the given location.
   */
  public Card getCard() {
    return this.card;
  }

  /**
   * will get the player related to the current BoardInput.
   *
   * @return the player associated with the boardInput.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * will add a pawn to the current implementation of BoardInputsPawns, adding it if it necessary.
   */
  public void addPawn() {
    if (this.pawns == null) {
      if (this.card == null) {
        this.pawns = BoardInputsPawns.ONE;
      }
    } else {
      int temp = this.pawns.getValue();
      if (temp == 1) {
        this.pawns = BoardInputsPawns.TWO;
      } else {
        this.pawns = BoardInputsPawns.THREE;
      }
    }
  }

  /**
   * will add the card to the given boardInput implementation (just uses the builder lol).
   */
  public void addCard(Card c) {
    this.card = c;
    this.pawns = null;
  }

  /**
   * general builder for BoardInput. This allows us to have a private constructor making it so
   * the user literally cannot make the object with the constructor, helping encapsulate the
   * functionality better.
   */
  public static class BoardInputBuilder {

    //the pawn enum that can be associated with the input
    private BoardInputsPawns pawns;

    //the card that could be associated with the given spot on the board
    private Card card;

    //the player that is associated with this given space
    private Player player;

    /**
     * initialize the fields to just default values (all null).
     */
    public BoardInputBuilder() {
      this.pawns = null;
      this.card = null;
      this.player = null;
    }

    /**
     * will set the pawn enum to be part of the board inputs.
     *
     * @param pawns the pawn enum that we are using in our object.
     * @return the builder object.
     */
    public BoardInputBuilder setPawns(BoardInputsPawns pawns) {
      if (pawns != null) {
        this.pawns = pawns;
      }
      return this;
    }

    /**
     * will set the card enum to be part of the board inputs.
     *
     * @param card the card that is actually on the board at the position.
     * @return the builder object.
     */
    public BoardInputBuilder setCard(Card card) {
      if (card != null) {
        this.card = card;
      }
      return this;
    }

    /**
     * will set the player of the given card to be whatever player we want.
     *
     * @param player the player that is related to the actual board spot.
     * @return the actual builder object.
     */
    public BoardInputBuilder setPlayer(Player player) {
      if (player != null) {
        this.player = player;
      }
      return this;
    }

    /**
     * the builder to create the relevant object that we want.
     *
     * @return the boardInput object that we wanted to build.
     */
    public BoardInput build() {
      return new BoardInput(this.pawns, this.card, this.player);
    }
  }
}
