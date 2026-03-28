package sanguine.controller;

import sanguine.model.Sanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.view.Position;
import sanguine.view.SanguineGuiView;

/**
 * represents the gui controller. This will allow for us to interact with the GUI.
 */
public class BasicSanguineController implements SanguineController, ViewFeaturesListener,
    ModelFeaturesListener {

  private final SanguineGuiView view;
  private final Sanguine model;
  private Position posn; //represents the values for the given move
  private Integer cardPos; //represents the posn for the given
  private final PlayerAction player; //the player for this given controller
  private Player currentTurnPlayer; //the player whose current turn it is
  private boolean isOver;
  private boolean wasGameOver;

  /**
   * generic constructor that will connect the gui and controller.
   *
   * @param view   the relevant view that we are using.
   * @param player is the player that this controller represents.
   * @throws IllegalArgumentException if any of the given arguments are null.
   */
  public BasicSanguineController(SanguineGuiView view, PlayerAction player,
                                 Sanguine model) {
    if (view == null || player == null || model == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.view = view;
    this.player = player;
    this.view.subscribe(this);
    this.model = model;
  }

  /**
   * basic method to start playing the game. This will start the game and subscribe it to the
   * model.
   */
  @Override
  public void playGame() {
    this.model.subscribe(this);
    this.currentTurnPlayer = this.player.getPlayer();
    this.view.makeVisible();
  }

  /**
   * basic method to, given the coordinates from the view, set the current position for the board.
   *
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  @Override
  public void mouseEventBoard(int x, int y) {
    if (!this.isOver) {
      if (this.player.getPlayer() == this.currentTurnPlayer) {
        this.view.setPosn(new Position(x, y));
        int col = y - 1;
        if (col == this.model.getCols()
            || col == -1) {
          return;
        }
        this.posn = new Position(x, col);
        this.view.refresh();
      }
    }
  }

  /**
   * will select the card from a given players hand by giving the index, and the player.
   *
   * @param cardIndex is the index of the card the user pressed in the hand.
   * @param p         is the player that this hand belong to.
   */
  @Override
  public void mouseEventHand(int cardIndex, Player p) {
    if (!this.isOver) {
      if (this.player.getPlayer() == this.currentTurnPlayer) {
        this.cardPos = cardIndex;
        this.view.clickCard(cardIndex);
        this.view.refresh();
      }
    }
  }

  /**
   * will determine what happens depending on what the key pressed is.
   *
   * @param key the key that the user pressed.
   */
  @Override
  public void keyClicked(String key) {
    if (!this.isOver) {
      if (this.player.getPlayer() == this.currentTurnPlayer) {
        if (key.equals("m")) {
          if (this.cardPos != null && this.cardPos >= 0
              && this.cardPos < this.model.getHandSize(this.currentTurnPlayer)) {
            Card card = this.model.getCardInHand(this.currentTurnPlayer, this.cardPos);
            BasicCard bc = (BasicCard) card;
            if (bc != null) {
              if (this.model.isValidMove(bc, this.posn.getX(), this.posn.getY(),
                  this.currentTurnPlayer)) {
                this.model.placeCard(bc, this.posn.getX(), this.posn.getY());
                this.view.clickCard(-1);
                this.view.setPosn(new Position(-1, -1));
              } else {
                this.view.showInvalidMove();
                this.view.clickCard(-1);
                this.view.setPosn(new Position(-1, -1));
              }
            } else {
              this.view.showInvalidMove();
              this.view.clickCard(-1);
              this.view.setPosn(new Position(-1, -1));
            }
          } else {
            this.view.showInvalidMove();
            this.view.clickCard(-1);
            this.view.setPosn(new Position(-1, -1));
          }
        } else if (key.equals("p")) {
          this.model.passMove();
          this.view.clickCard(-1);
          this.view.setPosn(new Position(-1, -1));
        }
      }
      this.posn = null;
      this.cardPos = null;
      this.view.refresh();
    }
  }

  /**
   * method to listen for whos turn it is and check if the game is over. If the game is over,
   * end the game, otherwise complete the move.
   *
   * @param player is the player whose turn it currently is.
   */
  @Override
  public void whoseTurn(Player player) {
    this.currentTurnPlayer = player;
    if (this.isOver && !this.wasGameOver) {
      if (model.totalScore(Player.PLAYER1) == model.totalScore(Player.PLAYER2)) {
        this.view.showGameOver(null, model.totalScore(Player.PLAYER1), "tie");
      } else {
        this.view.showGameOver(model.whoWon(), model.totalScore(model.whoWon()), "winner");
      }
      this.wasGameOver = true;
    }
    if (this.currentTurnPlayer == this.player.getPlayer()) {
      this.player.makeMove(this.model);
    }
  }

  /**
   * simple method to just say the game is over, listener from the model.
   *
   * @param player the player who won the game.
   */
  @Override
  public void gameOver(Player player) {
    this.isOver = true;
  }
}
