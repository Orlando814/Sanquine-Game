package sanguine;

import java.util.List;
import sanguine.controller.BasicSanguineController;
import sanguine.controller.MachineImpl;
import sanguine.controller.PlayerAction;
import sanguine.controller.PlayerImpl;
import sanguine.controller.SanguineController;
import sanguine.model.BasicSanguine;
import sanguine.model.ReadOnlyBasicSanguine;
import sanguine.model.ReadOnlySanguine;
import sanguine.model.Sanguine;
import sanguine.model.card.Card;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreator;
import sanguine.model.deck.DeckCreatorImpl;
import sanguine.strategy.FirstMove;
import sanguine.strategy.GreedyMove;
import sanguine.view.SanguineGuiFrame;
import sanguine.view.SanguineGuiView;

/**
 * This class contains the main method which launches the GUI version of the game.
 */
public final class SanguineGame {

  /**
   * This is the main method that runs the GUI version of the game.
   *
   * @param args are the provided user arguments.
   */
  public static void main(String[] args) {
    //we have to start with dealing with the default jar arguments
    if (args.length > 0) {
      final int rows = Integer.parseInt(args[0]);
      final int cols = Integer.parseInt(args[1]);
      String redPath = args[2];
      String bluePath = args[3];
      String p1 =  args[4];
      String p2 =  args[5];

      Sanguine model = new BasicSanguine();
      DeckCreator createDeck = new DeckCreatorImpl();
      final List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, redPath);
      final List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, bluePath);
      ReadOnlySanguine readOnlyModel = new ReadOnlyBasicSanguine(model);
      PlayerAction p1Action;

      PlayerAction p2Action;
      //switch for p1
      switch (p1) {
        case "human" -> p1Action = new PlayerImpl(Player.PLAYER1);
        case "strategy1" -> p1Action = new MachineImpl(Player.PLAYER1, new FirstMove());
        case "strategy2" -> p1Action = new MachineImpl(Player.PLAYER1, new GreedyMove());
        default -> p1Action = null;
      }
      //switch for p2
      switch (p2) {
        case "human" -> p2Action = new PlayerImpl(Player.PLAYER2);
        case "strategy1" -> p2Action = new MachineImpl(Player.PLAYER2, new FirstMove());
        case "strategy2" -> p2Action = new MachineImpl(Player.PLAYER2, new GreedyMove());
        default -> p2Action = null;
      }
      SanguineGuiView viewP1 = new SanguineGuiFrame(readOnlyModel, p1Action);
      SanguineGuiView viewP2 = new SanguineGuiFrame(readOnlyModel, p2Action);
      SanguineController controllerP1 = new BasicSanguineController(viewP1, p1Action, model);
      SanguineController controllerP2 = new BasicSanguineController(viewP2, p2Action, model);
      controllerP1.playGame();
      controllerP2.playGame();
      model.startGame(rows, cols, cardsPlayer1, cardsPlayer2);
      return;
    }

    Sanguine model = new BasicSanguine();
    DeckCreator createDeck = new DeckCreatorImpl();
    List<Card> cardsPlayer1 = createDeck.createDeck(Player.PLAYER1, "config/red.deck");
    List<Card> cardsPlayer2 = createDeck.createDeck(Player.PLAYER2, "config/red.deck");
    ReadOnlySanguine readOnlyModel = new ReadOnlyBasicSanguine(model);
    PlayerAction humanP1 = new PlayerImpl(Player.PLAYER1);
    PlayerAction humanP2 = new PlayerImpl(Player.PLAYER2);
    PlayerAction greedyP1 = new MachineImpl(Player.PLAYER1, new GreedyMove());
    PlayerAction greedyP2 = new MachineImpl(Player.PLAYER2, new GreedyMove());
    SanguineGuiView viewP1 = new SanguineGuiFrame(readOnlyModel, humanP1);
    SanguineGuiView viewP2 = new SanguineGuiFrame(readOnlyModel, greedyP2);
    SanguineController controllerP1 = new BasicSanguineController(viewP1, humanP1, model);
    SanguineController controllerP2 = new BasicSanguineController(viewP2, greedyP2, model);
    controllerP1.playGame();
    controllerP2.playGame();
    model.startGame(5, 7, cardsPlayer1, cardsPlayer2);
  }
}