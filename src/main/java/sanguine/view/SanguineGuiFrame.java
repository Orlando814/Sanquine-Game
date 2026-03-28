package sanguine.view;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import sanguine.controller.PlayerAction;
import sanguine.controller.PlayerActionsListener;
import sanguine.controller.ViewFeaturesListener;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.cell.Player;
import sanguine.strategy.MoveValues;

/**
 * This represents the Frame of the GUI that contains two panels. These two panels consist of the
 * board and the user's hand of cards.
 */
public class SanguineGuiFrame extends JFrame implements SanguineGuiView, PlayerActionsListener {

  private final SanguineBoardPanel board;
  private final SanguineHandPanel hand;
  private ViewFeaturesListener listener;
  private final ReadOnlySanguine model;

  /**
   * This is the constructor for the frame. Takes in a read-only version of the model and creates
   * the frame's size with a working close button. Also adds two frames (board and player's hand
   * of cards) to the frame. Also does some error checking.
   *
   * @param model is a read-only version of the model.
   */
  public SanguineGuiFrame(ReadOnlySanguine model, PlayerAction player) {
    super();
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model or player cannot be null.");
    }
    setSize(1000, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.model = model;
    this.board = new SanguineBoardPanel(model);
    this.board.setPreferredSize(new Dimension(this.getWidth(), 3 * (this.getHeight() / 4)));
    this.hand = new SanguineHandPanel(model, player);
    this.hand.setPreferredSize(new Dimension(this.getWidth(), (this.getHeight() / 4)));
    this.add(this.board);
    this.add(this.hand);
    player.subscribe(this);
  }

  /**
   * Refresh the view to reflect any changes in the game.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the game.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * will subscriber to the publisher.
   *
   * @param listener listens and shi to the listener.
   */
  @Override
  public void subscribe(ViewFeaturesListener listener) {
    this.listener = listener;
    board.subscribe(listener);
    hand.subscribe(listener);
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        listener.keyClicked(String.valueOf(e.getKeyChar()));
      }
    });
  }

  @Override
  public void clickCard(int cardIndex) {
    this.hand.clickCard(cardIndex);
  }

  /**
   * will set the posn in he board class to make sure that there is a way to highlight the location
   * of a given card.
   *
   * @param position the position we are doing this at.
   */
  public void setPosn(Position position) {
    this.board.setPosn(position);
  }

  @Override
  public void showInvalidMove() {
    showMessageDialog(this, "Invalid Move");
  }

  @Override
  public void showGameOver(Player player, int score, String type) {
    if (type.equals("winner")) {
      showMessageDialog(this, "Game Over"
          + System.lineSeparator() + "Winning Player: " + player.toString()
          + System.lineSeparator() + "Player Score: " + score);
    } else if (type.equals("tie")) {
      showMessageDialog(this, "Game Over"
          + System.lineSeparator() + "Tie Game");
    }
  }

  @Override
  public void hasMoveBeenMade(MoveValues move) {
    if (move == null) {
      listener.keyClicked("p");
    } else {
      listener.mouseEventBoard(move.getRow(), move.getCol() + 1);
      listener.mouseEventHand(this.model.getHand(move.getPlayer()).indexOf(move.getCard()),
          move.getPlayer());
      listener.keyClicked("m");
    }
  }
}
