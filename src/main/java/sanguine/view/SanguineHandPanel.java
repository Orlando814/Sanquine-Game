package sanguine.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import sanguine.controller.PlayerAction;
import sanguine.controller.ViewFeaturesListener;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;

/**
 * represents the panel for the cards at the bottom of the screen. Will allow for us to have
 * different behaviors for the cards and the boards without making a disgustingly long class.
 */
public class SanguineHandPanel extends JPanel {
  private final ReadOnlySanguine model;
  private final PlayerAction player;
  private int clickedCard;
  private List<Card> cards;

  /**
   * This is the constructor for this class. It first does some error checking and then adds
   * several frames equal to the number of cards in the player's hand to this panel.
   *
   * @param model  is the read-only version of the model.
   * @param player is the player that this hand belongs too.
   */
  public SanguineHandPanel(ReadOnlySanguine model, PlayerAction player) {
    super();
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model or player cannot be null.");
    }
    this.model = model;
    this.player = player;
    this.clickedCard = -1;
    this.cards = new ArrayList<>();
    this.setLayout(new GridLayout(1, 0, 0, 0));
    this.getCards();
  }

  /**
   * Will update the current cards in the hand by creating a new / updated version of the hand
   * deck and replacing the old one with the new one.
   */
  private void getCards() {
    List<Card> newCards = new ArrayList<>();
    for (int cardIndex = 0; cardIndex < model.getHandSize(player.getPlayer()); cardIndex++) {
      Card card = model.getCardInHand(player.getPlayer(), cardIndex);
      newCards.add(card);
    }
    this.cards = newCards;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    this.getCards();
    for (int cardIndex = 0; cardIndex < model.getHandSize(player.getPlayer()); cardIndex++) {
      if (this.clickedCard == cardIndex) {
        g2d.setColor(Color.MAGENTA);
      } else {
        if (this.player.getPlayer() == Player.PLAYER1) {
          g2d.setColor(new Color(255, 100, 105));
        } else {
          g2d.setColor(new Color(137, 207, 240));
        }
      }
      int cardWidth = this.getWidth() / model.getHandSize(this.player.getPlayer());
      g2d.fillRect(cardWidth * cardIndex, 0,
          cardWidth * cardIndex + cardWidth, this.getHeight());
    }
    g2d.setColor(Color.BLACK);
    this.drawCards(g2d);
  }

  /**
   * will draw the current cards, name, cost, influence, value.
   *
   * @param g2d the graphics object.
   */
  private void drawCards(Graphics2D g2d) {
    int fontSize = this.getHeight() / 10;
    int cardSize = this.getWidth() / model.getHandSize(this.player.getPlayer());
    Font font = new Font("Arial", Font.BOLD, fontSize);
    g2d.setFont(font);
    int cardCount = 0;
    for (Card card : this.cards) {
      String[] strCard = card.toString().split(System.lineSeparator());
      g2d.drawString(strCard[0], cardSize * cardCount, fontSize);
      g2d.drawString(strCard[1], cardSize * cardCount, fontSize * 2);
      g2d.drawString(strCard[2], cardSize * cardCount, fontSize * 3);
      for (int influenceRow = 3; influenceRow < strCard.length; influenceRow++) {
        g2d.drawString(strCard[influenceRow], cardSize * cardCount,
            this.getHeight() / 2 + (fontSize * (-3 + influenceRow)));
      }
      g2d.drawLine(cardSize * cardCount, 0, cardSize * cardCount, this.getHeight());
      g2d.drawLine(2 * cardSize * cardCount, 0, 2 * cardSize * cardCount, this.getHeight());
      cardCount++;
    }
  }

  /**
   * Subscribes a mouse listener to this frame that reports back the index of the card the user
   * clicked to the controller.
   *
   * @param listener is the controller which is listening to the input from this panel.
   */
  public void subscribe(ViewFeaturesListener listener) {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        double cardIndex = e.getX() * ((double) (model.getHandSize(player.getPlayer()))
            / getWidth());
        listener.mouseEventHand((int) cardIndex, player.getPlayer());
      }
    });
  }

  /**
   * This will highlight a card that the user has clicked.
   *
   * @param cardIndex is the index of the card that the user has clicked.
   */
  public void clickCard(int cardIndex) {
    this.clickedCard = cardIndex;
  }
}
