package sanguine.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import sanguine.controller.ViewFeaturesListener;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.card.BasicCard;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.Player;

/**
 * will represent the board. This will allow for us not to need to deal with any of the larger
 * things in the deck as well as the board in the same panel.
 */
public class SanguineBoardPanel extends JPanel {

  private final ReadOnlySanguine model;
  private Position posn;

  /**
   * will create a new board panel for our game.
   *
   * @param model the read only model that we are using.
   */
  public SanguineBoardPanel(ReadOnlySanguine model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.model = model;
    this.posn = null;
  }

  public void setPosn(Position posn) {
    this.posn = posn;
  }

  /**
   * represents the subscriber method for the listener.
   *
   * @param listener the inputted listener.
   */
  public void subscribe(ViewFeaturesListener listener) {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        double row = e.getY() * ((double) model.getRows() / getHeight());
        double col = e.getX() * ((double) (model.getCols() + 2) / getWidth());
        listener.mouseEventBoard((int) row, (int) col);
      }
    });
  }

  /**
   * The method that will paint the board itself, creating a new board.
   *
   * @param g the <code>Graphics</code> object to protect.
   */
  @Override
  protected void paintComponent(Graphics g) {
    //THIS WILL BASICALLY MAKE THE BOARD AND HAVE THE SCORE ON THE LEFT AND RIGHT
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    //lines for the board itself
    int rows = this.model.getRows();
    int cols = this.model.getCols() + 2; //doing this to have the left and right set to be empty
    g2d.scale(this.getWidth() / (cols * 60.0), this.getHeight() / (rows * 60.0));
    g2d.setColor(Color.GRAY);
    g2d.fillRect(60, 0, this.model.getCols() * 60, rows * 60);
    //draw the lines for the boards outline.
    g2d.setColor(Color.BLACK);
    drawLine(g2d, 0, 0, 0, rows);
    drawLine(g2d, 0, rows, cols, rows);
    drawLine(g2d, cols, rows, cols, 0);
    drawLine(g2d, cols, 0, 0, 0);
    //draws the left and right lines
    for (int row = 1; row < rows; row++) {
      drawLine(g2d, cols - 1, row, 1, row);
    }
    //draws the up and down lines
    for (int col = 1; col < cols; col++) {
      drawLine(g2d, col, 0, col, rows);
    }
    if (posn != null) {
      drawHighlights(g2d, posn.getX(), posn.getY());
    }
    g2d.setColor(Color.BLACK);
    drawRowScores(g2d);
    drawBoardInputs(g2d);
  }

  /**
   * will draw the highlighted version  of a given index if it was clicked.
   *
   * @param g2d the graphics object we are using
   * @param x the row of the index
   * @param y the col of the index
   */
  private void drawHighlights(Graphics2D g2d, int x, int y) {
    if (y == 0 || y == model.getCols() + 1) {
      return;
    }
    g2d.setColor(Color.MAGENTA);
    g2d.fillRect((60 * y), (60 * x), 60, 60);
  }

  /**
   * will draw all of the relevant things for the game, the pawns and the cards, etc.
   *
   * @param g2d is the 2D graphics object used to draw on this panel.
   */
  private void drawBoardInputs(Graphics2D g2d) {
    int scaleX = 24; // the center x value
    int scaleY = 36; // the center y value
    Font font = new Font("Arial", Font.PLAIN, 20);
    g2d.setFont(font);
    //double for loop to go through the whole board and check every input
    for (int rows = 0; rows < this.model.getRows(); rows++) {
      for (int cols = 0; cols < this.model.getCols(); cols++) {
        if (this.model.getInputAt(rows, cols) != null) {
          if (this.model.getInputAt(rows, cols).getPawns() != null) {
            drawPawns(g2d, rows, cols, scaleX, scaleY);
          }
          if (this.model.getInputAt(rows, cols).getCard() != null) {
            drawCards(g2d, rows, cols, scaleX, scaleY);
          }
        }
      }
    }
  }

  /**
   * will draw the cards at any index for the given board. This is a very simple thing, will,
   * very similar to the example Lucia gave us, make the given square red or blue and then
   * add the value of the card ontop of the square.
   *
   * @param g2d the graphics object.
   * @param rows the row that we are currently working with.
   * @param cols the col that we are currently working with.
   * @param scaleX the x coord of the center of a cell.
   * @param scaleY the y coord of the center of a call.
   */
  private void drawCards(Graphics2D g2d, int rows, int cols, int scaleX, int scaleY) {
    //start by making the given index a color
    BoardInput input = this.model.getInputAt(rows, cols);
    Player player = input.getPlayer();
    if (player == Player.PLAYER1) {
      g2d.setColor(new Color(255, 100, 105));
    } else {
      g2d.setColor(new Color(137, 207, 240));
    }
    //make the box with the color
    g2d.fillRect((60 * (cols + 1)), (60 * rows), 60, 60);
    //make the text itself ontop of the box
    g2d.setColor(Color.BLACK);
    BasicCard bc = (BasicCard) input.getCard();
    int value = bc.getValue();
    String temp = "" + value;
    g2d.drawString(temp, scaleX + (60 * (cols + 1)), scaleY + (60 * rows));
  }

  /**
   * will draw the pawns at any index for the given board.
   *
   * @param g2d the graphics object.
   * @param rows the row that we are currently working with.
   * @param cols the col that we are currently working with.
   * @param scaleX the x coord of the center of a cell.
   * @param scaleY the y coord of the center of a call.
   */
  private void drawPawns(Graphics2D g2d, int rows, int cols, int scaleX, int scaleY) {
    if (this.model.getInputAt(rows, cols).getPlayer() == Player.PLAYER1) {
      g2d.setColor(new Color(255, 100, 105));
    } else {
      g2d.setColor(new Color(137, 207, 240));
    }
    String temp = "" + this.model.getInputAt(rows, cols).getPawns().getValue();
    g2d.drawString(temp, scaleX + (60 * (cols + 1)), scaleY + (60 * rows));
  }

  /**
   * will draw the scores for player 1 and player 2 respectively.
   *
   * @param g2d the graphics interface that we are using.
   */
  private void drawRowScores(Graphics2D g2d) {
    int scaleX = 24;
    int scaleY = 36;
    //because I want times new roman and a larger size I can mess around with
    Font font = new Font("Arial", Font.PLAIN, 20);
    g2d.setFont(font);

    for (int row = 0; row < this.model.getRows(); row++) {
      //draw the thingies in the middle of the row
      String temp = "" + model.rowScore(row, Player.PLAYER1);
      g2d.drawString(temp, scaleX, scaleY + (60 * row));
    }

    for (int row = 0; row < this.model.getRows(); row++) {
      //draw the thingies in the middle of the row
      String temp = "" + model.rowScore(row, Player.PLAYER2);
      g2d.drawString(temp, scaleX + 60 * (model.getCols() + 1), scaleY + (60 * row));
    }
  }

  /**
   * funny haha lol.
   *
   * @param g2d graphics object
   * @param x1 x1 of the line
   * @param y1 y1 of the line
   * @param x2 x2 of the line
   * @param y2 y2 of the line
   */
  private void drawLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
    double modelRowToLogicalY = 60.0;
    double modelColumnToLogicalX = 60.0;

    g2d.drawLine((int) (x1 * modelColumnToLogicalX),  (int) (y1 * modelRowToLogicalY),
        (int) (x2 * modelColumnToLogicalX), (int) (y2 * modelRowToLogicalY));
  }
}
