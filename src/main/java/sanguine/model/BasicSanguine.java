package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ModelFeaturesListener;
import sanguine.model.card.BasicCard;
import sanguine.model.card.Card;
import sanguine.model.card.CardInfluence;
import sanguine.model.cell.BoardInput;
import sanguine.model.cell.BoardInputsPawns;
import sanguine.model.cell.Player;
import sanguine.model.deck.DeckCreatorImpl;

/**
 * will represent the game of sanguine. Utilizing exceptions, similar to that of klondike, to
 * tell our other parts of the controller what is happening in our interface. IAE and ISE for
 * invalid args and invalid moves respectively.
 */
public class BasicSanguine extends DeckCreatorImpl implements Sanguine {

  //represents the board for the game, represented as a 2d array
  private List<List<BoardInput>> board;
  //player1 deck, list of cards
  private List<Card> playerOneCards;
  //player1's actual hand that they have
  private List<Card> playerOneHand;
  //player2 deck, list of cards
  private List<Card> playerTwoCards;
  //player2's actual hand that they have
  private List<Card> playerTwoHand;
  //the columns of the given game of sanguine
  private int cols;
  //the rows of the given game of sanguine
  private int rows;
  //represents the current player, either p1 or p2
  private Player curPlayer;
  //represents the number of times the previous move was passed (just used for game Over basically)
  private int consecutivePasses;
  //represents if the game has started, just makes sure that the game is started before any mo
  //moves or actions can be done.
  private boolean gameStarted;
  private final List<ModelFeaturesListener> listeners;

  /**
   * will just instantiate all the values to their relevant values.
   */
  public BasicSanguine() {
    this.board = null;
    this.cols = 0;
    this.rows = 0;
    this.playerOneCards = new ArrayList<>();
    this.playerOneHand = new ArrayList<>();
    this.playerTwoCards = new ArrayList<>();
    this.playerTwoHand = new ArrayList<>();
    this.consecutivePasses = 0;
    this.curPlayer = Player.PLAYER1; //INVARIANT: This will only be Player.PLAYER1 or Player.PLAYER2
    this.gameStarted = false;
    this.listeners = new ArrayList<>();
  }

  /**
   * represents the starting of the game. Will make sure that we have valid rows and columns and
   * throw illegal argument exceptions as necessary.
   *
   * @param rows the inputted amount of rows that our board will have.
   * @param cols the inputted amount of columns that our code will have.
   * @param playerOneDeck the inputted deck for player 1.
   * @param playerTwoDeck the inputted deck for player 2.
   * @throws IllegalArgumentException When the row or column is < 0 and when the columns aren't odd.
   * @throws IllegalStateException if the game hasn't started.
   */
  @Override
  public void startGame(int rows, int cols, List<Card> playerOneDeck, List<Card> playerTwoDeck)
      throws IllegalArgumentException, IllegalStateException {
    //just a generic check to make sure game has not already started
    if (this.gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Rows and columns must be positive");
    }
    if (cols % 2 == 0) {
      throw new IllegalArgumentException("Columns must be odd");
    }
    this.rows = rows;
    this.cols = cols;
    this.board = new ArrayList<>();
    for (int row = 0; row < this.rows; row++) {
      ArrayList<BoardInput> rowList = new ArrayList<>();
      for (int col = 0; col < this.cols; col++) {
        rowList.add(null);
      }
      this.board.add(rowList);
    }
    createBoard();
    this.playerOneCards = playerOneDeck;
    this.playerTwoCards = playerTwoDeck;
    this.playerOneHand = createHand(this.playerOneCards);
    this.playerTwoHand = createHand(this.playerTwoCards);
    this.gameStarted = true;
    this.notifyListeners();
  }

  /**
   * will basically just check if the game has started or not and throw an exception if it hasn't.
   *
   * @throws IllegalArgumentException if the game has not started
   */
  private void isGameStarted() throws IllegalArgumentException {
    if (!(this.gameStarted)) {
      throw new IllegalStateException("Game has not started");
    }
  }

  /**
   * will take in a set of cards and make a smaller deck with those cards.
   * will create the hand with a third of the cards in the deck.
   *
   * @param playerCards the cards that the player inputted
   * @return the hand that we just created from the players deck of cards.
   */
  private List<Card> createHand(List<Card> playerCards) {
    int cardCount = playerCards.size() / 3;
    List<Card> hand = new ArrayList<>();
    for (int cardIndex = 0; cardIndex < cardCount; cardIndex++) {
      hand.add(playerCards.removeFirst()); //because we just want to keep taking the first
    }
    return hand;
  }

  /**
   * will create the board as a 2d array, will put a p1 pawn in each of the left, and a p2 pawn
   * in each of the right side of the board.
   */
  private void createBoard() {
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        if (col == 0) { //reference to the first column, player ones single pawn col
          Player p1 = Player.PLAYER1;
          setPlayerPawns(p1, this.board, row, col);
          continue;
        }
        if (col == this.board.get(row).size() - 1) { //reference to the last column, player twos
          Player p2 = Player.PLAYER2;
          setPlayerPawns(p2, this.board, row, col);
          continue;
        }
        this.board.get(row).set(col, null);
      }
    }
  }

  /**
   * mainly made to abstract and make it slightly more readable. Will take in the board and player
   * and then make the pawns given that player and the board, then adding it to the board,
   * this will mutate the object.
   *
   * @param tempBoard the board that we are using.
   * @param row       the row of the given place where we are adding this
   * @param col       the col of the given place where we are adding this
   */
  private void setPlayerPawns(Player p, List<List<BoardInput>> tempBoard, int row, int col) {
    BoardInputsPawns pawns = BoardInputsPawns.ONE;
    tempBoard.get(row).set(col, new BoardInput.BoardInputBuilder()
        .setPawns(pawns)
        .setPlayer(p)
        .build());
  }

  /**
   * Will place the card at the specified row and column.
   *
   * @param row the row, indexed at 0
   * @param col the column, indexed at 0
   * @throws IllegalArgumentException will throw if there is an invalid row or column. that meaning
   *                                  it is too large for the size of our board or negative.
   * @throws IllegalStateException    will throw if the move we attempt to do is invalid.
   */
  @Override
  public void placeCard(Card c, int row, int col) throws
      IllegalArgumentException, IllegalStateException {
    isGameStarted(); //check if game started

    if (!(isValidMovePrivate((BasicCard) c, row, col, curPlayer))) {
      throw new IllegalStateException("Invalid move");
    }
    //now we can actually do the move itself, basically just making a new object for the curPos
    if (this.curPlayer == Player.PLAYER1) { //check which player we are
      this.playerOneHand.remove(c);
      place(c, row, col);
      if (!(this.playerOneCards.isEmpty())) {
        this.playerOneHand.add(this.playerOneCards.removeFirst());
      }
      this.curPlayer = Player.PLAYER2;
    } else {
      this.playerTwoHand.remove(c);
      place(c, row, col);
      if (!(this.playerTwoCards.isEmpty())) {
        this.playerTwoHand.add(this.playerTwoCards.removeFirst());
      }
      this.curPlayer = Player.PLAYER1;
    }
    this.consecutivePasses = 0;
    this.notifyListeners();
  }

  /**
   * will check to see if a given move is valid for a card.
   *
   * @param bc the relevant card that we are using.
   * @param row the row that we are trying to move the card to.
   * @param col the col that we are trying to move the card to.
   * @param player the player we are checking the move of.
   * @return the boolean that represents if the move is valid or not.
   */
  public boolean isValidMove(BasicCard bc, int row, int col, Player player) {
    checkInvalidIndexes(row, col);
    BoardInput curPos = getInputAt(row, col);
    if (curPos == null) {
      return false;
    }
    if (curPos.getPawns() == null) {
      return false;
    } else {
      if (bc.getCost() > curPos.getPawns().getValue()) {
        return false;
      }
    }
    return curPos.getPlayer() == player;
  }

  /**
   * will check to see if a given move is valid for a card.
   *
   * @param bc the relevant card that we are using.
   * @param row the row that we are trying to move the card to.
   * @param col the col that we are trying to move the card to.
   * @param player the player we are checking the move of.
   * @return the boolean that represents if the move is valid or not.
   */
  private boolean isValidMovePrivate(BasicCard bc, int row, int col, Player player) {
    checkInvalidIndexes(row, col);
    BoardInput curPos = getBoardInput(row, col);
    if (bc.getCost() > curPos.getPawns().getValue()) {
      return false;
    }
    return curPos.getPlayer() == player;
  }

  /**
   * will get the board input and then do a few checks to make sure that its valid.
   *
   * @param row the row of the checked index
   * @param col the col of the checked index
   * @return the boardInput itself if it's valid.
   */
  private BoardInput getBoardInput(int row, int col) {
    BoardInput curPos = this.board.get(row).get(col);
    //done to check whether we can actually see if anything is here
    if (curPos == null) {
      throw new IllegalStateException("Invalid position to place a card, no pawns");
    }
    //now we check if there is a card at this position, if there is not we move forward to see if
    //there are enough pawns to place the requested card
    if (curPos.getCard() != null) {
      throw new IllegalStateException("Cannot place a card on another card");
    }
    return curPos;
  }

  /**
   * will do the big logic to placing the card to the array and then adding the pawns and other
   * references.
   *
   * @param c   the card itself.
   * @param row the row that the card is placed at 0 indexed (we know it is valid).
   * @param col the col that the card is placed at 0 indexed (we know it is valid).
   */
  private void place(Card c, int row, int col) {
    //variable to steal the influence of the card itself
    BasicCard bc = (BasicCard) c;
    List<List<CardInfluence>> influence = bc.getArray();
    //looping through the influence array basically
    for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
      for (int colIndex = 0; colIndex < 5; colIndex++) {
        checkInfluenceAndMakeChanges(c, row, col, rowIndex, colIndex, influence);
      }
    }
  }

  /**
   * will go through and do the checks and then make the changes as necessary to the cell on the
   * board.
   *
   * @param c         the card that we are using in this scenario.
   * @param row       the row of the influence that we are currently using.
   * @param col       the col of the influence we are currently using.
   * @param rowIndex  the rowIndex of the board itself.
   * @param colIndex  the colIndex of the board itself.
   * @param influence the influence array of the card itself.
   */
  private void checkInfluenceAndMakeChanges(Card c, int row, int col, int rowIndex, int colIndex,
                                            List<List<CardInfluence>> influence) {
    int tempCol;
    int tempRow;
    tempRow = row - 2 + rowIndex;
    tempCol = col - 2 + colIndex;
    CardInfluence cur = influence.get(rowIndex).get(colIndex);
    if (tempRow >= this.rows || tempRow < 0 //row checks
        || tempCol >= this.cols || tempCol < 0) { //col checks
      return;
    } else {
      if (cur == CardInfluence.INFLUENCE) {
        doCardInfluenceMove(tempRow, tempCol);
      }
      if (this.board.get(tempRow).get(tempCol) != null) {
        if (cur == CardInfluence.CARD) {
          this.board.get(tempRow).get(tempCol).addCard(c);
        }
      }
    }
  }

  /**
   * will do all teh heavy lifting for adding pawns, overriding them, and making null spaces
   * have pawns.
   *
   * @param tempRow the row that we are using for the board of the game
   * @param tempCol the col that we are using for the board of the game
   */
  private void doCardInfluenceMove(int tempRow, int tempCol) {
    //separate check for if there is a card, just ret if there is
    if (this.board.get(tempRow).get(tempCol) != null) {
      if (this.board.get(tempRow).get(tempCol).getCard() != null) {
        return;
      }
    }
    //check within to see if we have the current players pawns, if so add, otherwise
    //make a new one if it is the other players and override
    if (this.board.get(tempRow).get(tempCol) != null) {
      if (this.curPlayer == board.get(tempRow).get(tempCol).getPlayer()) {
        this.board.get(tempRow).get(tempCol).addPawn();
      } else {
        this.board.get(tempRow).set(tempCol, (new BoardInput.BoardInputBuilder()
            .setPawns(BoardInputsPawns.ONE)
            .setPlayer(this.curPlayer)
            .build()));
      }
    } else {
      this.board.get(tempRow).set(tempCol, (new BoardInput.BoardInputBuilder()
          .setPawns(BoardInputsPawns.ONE)
          .setPlayer(this.curPlayer)
          .build()));
    }
  }

  /**
   * will check to see if the rows and columns are of valid values.
   *
   * @param row the 0 indexed row (hopefully)
   * @param col the 0 indexed col (hopefully)
   * @throws IllegalArgumentException if the vals are invalid.
   */
  private void checkInvalidIndexes(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Rows and columns must be positive");
    }
    if (row >= this.rows) {
      throw new IllegalArgumentException("Rows must be between 0 and " + (this.rows - 1));
    }
    if (col >= this.cols) {
      throw new IllegalArgumentException("Columns must be between 0 and " + (this.cols - 1));
    }
  }

  /**
   * will get the score of a given row of the board.
   *
   * @param row the inputted row indexed at 0
   * @param p   the player that we want to calculate the score for
   * @return the score of the given row.
   * @throws IllegalArgumentException if the row is invalid
   */
  @Override
  public int rowScore(int row, Player p) throws IllegalArgumentException {
    if (row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("Rows must be between 0 and " + (this.rows - 1));
    }
    //just check the value, if there is a card on there add the value if it is for the given player
    int score = 0;
    for (int col = 0; col < this.cols; col++) {
      if (this.board.get(row).get(col) == null) {
        continue;
      }
      if (this.board.get(row).get(col).getCard() != null) {
        if (this.board.get(row).get(col).getPlayer() == p) {
          BasicCard bc = (BasicCard) this.board.get(row).get(col).getCard();
          score += bc.getValue();
        }
      }
    }
    return score;
  }

  /**
   * the total score for the given player.
   *
   * @param p the given player that we want the score of
   * @return the given score of that player as a total num
   */
  @Override
  public int totalScore(Player p) {
    Player otherPlayer;
    if (Player.PLAYER1 == p) {
      otherPlayer = Player.PLAYER2;
    } else {
      otherPlayer = Player.PLAYER1;
    }
    int score = 0;
    for (int row = 0; row < this.rows; row++) {
      if (rowScore(row, otherPlayer) < rowScore(row, p)) {
        score += rowScore(row, p);
      }
    }
    return score;
  }

  /**
   * will take in a player and then pass their move if they so desire.
   */
  @Override
  public void passMove() {
    if (this.curPlayer == Player.PLAYER2) {
      curPlayer = Player.PLAYER1;
      if (!(this.playerTwoCards.isEmpty())) {
        this.playerTwoHand.add(this.playerTwoCards.removeFirst());
      }
      this.consecutivePasses++;
      this.notifyListeners();
      return;
    } else {
      curPlayer = Player.PLAYER2;
      if (!(this.playerOneCards.isEmpty())) {
        this.playerOneHand.add(this.playerOneCards.removeFirst());
      }
      this.consecutivePasses++;
      this.notifyListeners();
    }
  }

  /**
   * will determine if the game is over, basically if both of the players passed consecutively.
   *
   * @return a boolean saying if the game is over or not.
   */
  @Override
  public Boolean isGameOver() {
    isGameStarted();
    return (this.consecutivePasses > 1);
  }

  /**
   * will, when called, ONLY WHEN THE GAME IS OVER, return the player that won the game.
   *
   * @return the player who has won the game itself.
   */
  @Override
  public Player whoWon() throws IllegalStateException {
    if (!(this.isGameOver())) {
      throw new IllegalStateException("Game is not over");
    }
    if (totalScore(Player.PLAYER1) > totalScore(Player.PLAYER2)) {
      return Player.PLAYER1;
    }
    if (totalScore(Player.PLAYER1) < totalScore(Player.PLAYER2)) {
      return Player.PLAYER2;
    }
    throw new IllegalStateException("Tie?");
  }

  /**
   * will get the amount of rows IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  @Override
  public int getRows() {
    return this.rows;
  }

  /**
   * will get the amount of cols IN THE BOARD of the game.
   *
   * @return an integer representation of the num of rows.
   */
  @Override
  public int getCols() {
    return this.cols;
  }

  /**
   * will get the given BoardInput at the row and col specified. Will only do so if the row and
   * col are valid inputs.
   *
   * @param row the row indexed at 0.
   * @param col the col indexed at 0.
   * @return the BoardInput class which is silly.
   */
  @Override
  public BoardInput getInputAt(int row, int col) {
    checkInvalidIndexes(row, col);
    BoardInput temp = this.board.get(row).get(col);
    if (temp == null) {
      return null;
    }
    return new BoardInput.BoardInputBuilder()
        .setPawns(temp.getPawns())
        .setPlayer(temp.getPlayer())
        .setCard(temp.getCard())
        .build();
    //return this.board.get(row).get(col);
  }

  /**
   * will get the handSize of the given player at any given moment, mainly, this method is for
   * testing.
   *
   * @param p the player that we want the size of the hand of.
   * @return an integer representing the size of the hand.
   */
  @Override
  public int getHandSize(Player p) {
    if (p == Player.PLAYER1) {
      return this.playerOneHand.size();
    }
    return this.playerTwoHand.size();
  }

  /**
   * will get the hand for a given player and return it.
   *
   * @param p the player that we are getting the hand for.
   * @return the hand itself as a list of cards.
   */
  @Override
  public List<Card> getHand(Player p) {
    if (p == Player.PLAYER1) {
      return new ArrayList<>(this.playerOneHand);
    }
    return new ArrayList<>(this.playerTwoHand);
  }

  /**
   * will get the hand for the given player.
   *
   * @param p     the players hand we are referencing.
   * @param index the index within their hand that we are referencing.
   * @return a copy of the card that is being returned.
   */
  @Override
  public Card getCardInHand(Player p, int index) {
    if (p == Player.PLAYER1) {
      return getCardInPlayer1Hand(index);
    }
    return getCardInPlayer2Hand(index);
  }

  /**
   * will get the card in player 1s hand also throwing an exception if the index is out of bounds.
   *
   * @param index the index in the hand.
   * @return the immutable card at that index.
   */
  private Card getCardInPlayer1Hand(int index) {
    if (index < 0 || index >= this.playerOneHand.size()) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    return this.playerOneHand.get(index);
  }

  /**
   * will get the card in player 2s hand also throwing an exception if the index is out of bounds.
   *
   * @param index the index in the hand.
   * @return the immutable card at that index.
   */
  private Card getCardInPlayer2Hand(int index) {
    if (index < 0 || index >= this.playerTwoHand.size()) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    return this.playerTwoHand.get(index);
  }

  /**
   * will get the player for a given turn.
   *
   * @return the current player whose turn it is.
   */
  @Override
  public Player getPlayer() {
    return this.curPlayer;
  }

  @Override
  public void subscribe(ModelFeaturesListener listener) {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  /**
   * Notifies all the listeners that are currently subscribed to this.
   */
  private void notifyListeners() {
    for (ModelFeaturesListener listener : this.listeners) {
      listener.whoseTurn(this.curPlayer);
      if (this.isGameOver()) {
        if (this.totalScore(Player.PLAYER1) == totalScore(Player.PLAYER2)) {
          listener.gameOver(Player.PLAYER1);
        } else {
          listener.gameOver(this.whoWon());
        }
      }
    }
  }
}
