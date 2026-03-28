package sanguine.controller.mocks;

import java.io.IOException;
import sanguine.controller.ModelFeaturesListener;
import sanguine.controller.SanguineController;
import sanguine.controller.ViewFeaturesListener;
import sanguine.model.cell.Player;

/**
 * mock for our sanguine controller, will just go through the steps and make sure that everything
 * works as intended while unit testing.
 */
public class SanguineControllerMock implements SanguineController, ViewFeaturesListener,
    ModelFeaturesListener {

  Appendable log;

  /**
   * mock constructor with our log.
   *
   * @param log the stringbuilder that we are using to test if we are going through things
   *            properly.
   */
  public SanguineControllerMock(Appendable log) {
    if (log == null) {
      throw new IllegalArgumentException("Log has to like not be null pookie");
    }
    this.log = log;
  }

  @Override
  public void playGame() {
    try {
      log.append("Started Game\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }

  @Override
  public void whoseTurn(Player player) {
    try {
      log.append("Called whose turn with Player: " + player + "\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }

  @Override
  public void gameOver(Player player) {
    try {
      log.append("Called gameOver with player: " + player + "\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }

  @Override
  public void mouseEventBoard(int x, int y) {
    try {
      log.append("Called mouseEventBoard with X: " + x + " Y: " + y + "\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }

  @Override
  public void mouseEventHand(int cardIndex, Player player) {
    try {
      log.append("Called mouseEventHand with cardIndex: " + cardIndex + " and player: "
          + player + "\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }

  @Override
  public void keyClicked(String key) {
    try {
      log.append("Called keyClicked with key: " + key + "\n");
    } catch (IOException e) {
      int sixSeven = 67;
    }
  }
}
