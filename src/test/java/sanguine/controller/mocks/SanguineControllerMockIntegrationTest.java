package sanguine.controller.mocks;

import sanguine.controller.ModelFeaturesListener;
import sanguine.controller.PlayerAction;
import sanguine.controller.SanguineController;
import sanguine.controller.ViewFeaturesListener;
import sanguine.model.Sanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.view.Position;
import sanguine.view.SanguineGuiView;

/**
 * Represents a mock of the controller used for integration testing. Functions exactly the same as
 * the regular controller except it doesn't display any messages when an invalid move is played
 * or the game ends.
 */
public class SanguineControllerMockIntegrationTest implements SanguineController,
    ViewFeaturesListener, ModelFeaturesListener {

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
  public SanguineControllerMockIntegrationTest(SanguineGuiView view, PlayerAction player,
                                               Sanguine model) {
    if (view == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.view = view;
    this.player = player;
    this.view.subscribe(this);
    this.model = model;
  }

  @Override
  public void playGame() {
    this.model.subscribe(this);
    this.currentTurnPlayer = this.player.getPlayer();
    this.view.makeVisible();
  }

  @Override
  public void mouseEventBoard(int x, int y) {
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

  @Override
  public void mouseEventHand(int cardIndex, Player p) {
    if (this.player.getPlayer() == this.currentTurnPlayer) {
      this.cardPos = cardIndex;
      this.view.clickCard(cardIndex);
      this.view.refresh();
    }
  }

  /**
   * will determine what happens depending on what the key pressed is.
   *
   * @param key the key that the user pressed.
   */
  @Override
  public void keyClicked(String key) {
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
              this.view.clickCard(-1);
              this.view.setPosn(new Position(-1, -1));
            }
          } else {
            this.view.clickCard(-1);
            this.view.setPosn(new Position(-1, -1));
          }
        } else {
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

  @Override
  public void whoseTurn(Player player) {
    this.currentTurnPlayer = player;
    if (this.isOver && !this.wasGameOver) {
      this.wasGameOver = true;
    }
    if (this.currentTurnPlayer == this.player.getPlayer()) {
      this.player.makeMove(this.model);
    }
  }

  @Override
  public void gameOver(Player player) {
    this.isOver = true;
  }
}
