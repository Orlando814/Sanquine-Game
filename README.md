# Overview
This code represents a copy of the Final Fantasy 6 minigame
Queens Blood. Currently, the model and a textual view have
been implemented, but there will be a controller and GUI
eventually. The only prerequisites for this code is having
the IDE installed with Java.

# Quick Start
To start code provide a single line representing the file path to the deck config file:

    "docs/red.deck"

Any extra arguments will cause the main to error.

# Key Components
There currently two main components to this game.
- Model:(BasicSanguine)
    - This is the component that represents the game itself and is able to change its internal state.
    - Drives the program as it doesn't require any other key components to function

- View:(Sanguine)
    - The view is driven by the model as it creates a visual
      representation based on the model's current state and cannot exist without the model.

# Key Subcomponents
- Model Subcomponents:
    - ReadCardFile:
        - Will create a list of cards in their String representation from a config file.
        - Has several methods to get specific information from a specific card in that list (name, cost, value, influence)
        - Used to create BasicCards
    - BasicCard:
        - Represents a card in the game with a name, cost, value, and influence
        - Used to play the game as a card in a deck
    - DeckCreator:
        - Has been created to construct a deck of cards using the ReadCardFile class and the player this deck belongs too
        - Used in the model to create the decks that the players will use
    - BoardInput:
        - Represents what values the board can be at any given time (card, influence, or nothing)
        - Created to make board implementation easier and has methods which make board modification simple
        - Used in creating and modify the state of the model's board

# Source Organization
- Main:
    - Contains implementation of controller, model, and view. In each package there will be several different
      class and subfolders. Subfolder generally represents classes that are subcomponents of the parent package.
      Ex: Cell package contains classes about the board's cells and is in the model package.
- Test:
    - Will also have a similar layout as the main except instead of implementation there tests for that specific piece
      Ex: In the model package there might be a cell package that has classes which tests the implementations of the various classes
      that are present in the cell package in the main directory
    - Might have a mocks folder which will contain fake classes used for specific testing. Generally related to controller,
      but not allways. Ex: Mock package might contain a fake implementation of an Appendable that always throws an IOException
      which is used to make sure that exception is handled properly.

# AI Implementation Ideas
Creating an AI for the game sounds challenging, and depending on how it's done can be very difficult.
However, to simplify the process we are thinking about using a greedy algorithm. What we mean by this is
creating an AI which looks at the current state of the board and makes the move it that scores it the most points.
It's greedy because it just is trying to maximize the score on every turn versus having any foresight and
planning ahead.
- Interface Method Ideas:
    - By utilizing the public methods in the model, such as getInputAt and some of the methods in the BoardInput class,
      we can find both the value of what is in the given cell, but also who owns what is in the cell. This allows for
      the AI to see what is on the board, what belongs to them, and how the game would react. In regards to how it would
      make decisions, it can use the total score method or the row score method to maximize its score and see how its row
      score compares to the other players to see if it is even worth placing a card in such a row. This would allow
      the AI to have all necessary functions, being able to see the board, get the scores necessary for it to find the
      best move, and place the card with the general place card method.

# Changes for part 2
Created methods for our read only method to make implementation of the view and controller easier for ourselves later 
on. This means we added methods that would:
- Get the card given an index and a players hand.
  - This is so that when the controller is given indexes and a player, we can immediately fetch the card
  - that is being referenced.
- Check if a given move is valid by inputting the row, col, and card. 
  - This is also very similar, allows us to get a boolean if the move is valid to see if it is even worth checking
  - when writing our AI, checking moves the user makes. 
- Get the player that is currently playing at this instance
  - This one is mainly so that we can make sure that the proper player is playing by allowing the controller
  - to know whos turn it currently is.

The reason that we decided to implement these methods was mainly for the sake of making the controller and view 
implementation easier. The way that this was namely brought to our attention was through the review session in class.
This gave us many ideas and also let us know that we were on the right track when initially implementing relieving a 
a lot of our fears. 

# Passing and Moving

- If the user would like to pass their move, we have implemented so that "p" will pass their move.
- If the user would like to move their card, we have implemented so that "m" will do the move itself.

  - For both of these cases, it is merely a stub implementation which does not check anything, it merely will 
  check to see if the key was pressed, and if it was, print to the console with "passed move" if the pressed key was"
  "p" or "played move" if the pressed key was "m".

# View Documentation

For our view, we created a few new classes, namely a class for the large frame, a class for the board panel, a class
for the hand panel, and a class for the card panel.

- Board Panel
  - this will directly create the board and deal with the mouse inputs.
- Hand Panel
  - this will directly create the hand for the user by delegating to the card panel to create 
  the cards, this will be elaborated on in the card panel. This method will also  draw the colored background of the 
  cards.
- Card Panel
  - made to allow for us to use a flow layout so we don't need to deal with changing the size of the cards
  depending on how large the hand is. Instead, we just let java do it. This class will draw the information of the cards.

# Controller Documentation

Mainly just stub implementations, most of it is explained above.

- We utilized the publisher subscriber pattern to connect the view to the controller and allow for them
to interact with each other as we intend. This is done for our method which checks where we clicked on the board,
where we clicked on the hand. Additionally, we just have a simple thing to check for the input in the controller, 
just a simple for loop that will check if "m" was pressed or "p" was pressed and do the relevant printing to the 
output.

# Strategies

We implemented the strategies using the first two implementations given, the greedy method and the first fit. The way 
we implemented this as follows.
 - The first greedy implementation will check to see how the row score increases and will only care about the increase
in value of the row scores unless it increases the row score where:
   - the player can increase their row score above the other players row score
 - When implementing these two, we will also favor the first card, left to right in the cols, then top to bottom on the
rows. This being said, if there is a tie in this, it will favor the first instance that has that given increase in 
score.

- First fit is as it seems, we loop through the cards and then will place the first card we can in the first location
we can. We loop through the cards from left to right and loop through the board from left to right in the cols, and
 top to bottom in the rows.

- The last implementation is just passing the move, this will return null which our controller, when implementing the
actual moves, will know is representing a "passed" move.

- This strategy has the transcripts written in the docs folder, named as requested in the assignment.

# Jar File

- Make sure to run the jar in the home directory so that it can properly read the file for the deck. Other than that, 
it should properly function and allow you to see the 5 row by 7 column board!

# File Tree

- +- config/
- | +- blue.deck
- | +- fuckedup.deck
- | +- red.deck
- | +- testingExample.deck
- +- images/
- | +- Screenshot1 - Game Start.png
- | +- Screenshot2 - cards selected(Red).png
- | +- Screenshot3 - cards selected(Blue).png
- | +- Screenshot4 - MidGame.png
- +- src/
- | +- main/
- | | +- java/
- | | | +- sanguine/
- | | | | +- controller/
- | | | | | +- BasicSanguineController
- | | | | | +- MachineImpl
- | | | | | +- ModelFeaturesListener
- | | | | | +- MoveEnum
- | | | | | +- PlayerAction
- | | | | | +- PlayerActionsListener
- | | | | | +- PlayerImpl
- | | | | | +- SanguineController
- | | | | | +- ViewFeaturesListener
- | | | | +- model/
- | | | | | +- card/
- | | | | | | +- BasicCard
- | | | | | | +- Card
- | | | | | | +- CardExtras
- | | | | | | +- CardInfluence
- | | | | | | +- CardReader
- | | | | | | +- Cost
- | | | | | | +- ReadCardFile
- | | | | | +- cell/
- | | | | | | +- BoardInput
- | | | | | | +- BoardInputsPawns
- | | | | | | +- Player
- | | | | | +- deck/
- | | | | | | +- DeckCreator
- | | | | | | +- DeckCreatorImpl
- | | | | | +- BasicSanguine
- | | | | | +- ReadOnlyBasicSanguine
- | | | | | +- ReadOnlySanguine
- | | | | | +- Sanguine
- | | | | +- strategy/
- | | | | | +- FirstMove
- | | | | | +- GreedyMove
- | | | | | +- MoveValues
- | | | | | +- PassMove
- | | | | | +- Strategy
- | | | | +- view/
- | | | | | +- Position
- | | | | | +- SanguineBoardPanel
- | | | | | +- SanguineCardPanel
- | | | | | +- SanguineGuiFrame
- | | | | | +- SanguineGuiView
- | | | | | +- SanguineHandPanel
- | | | | | +- SanguineTextualView
- | | | | | +- SanguineTextualViewImpl
- | | | | +- Sanguine
- | | | | +- SanguineGame
- | +- test/
- | | +- java/
- | | | +- sanguine/
- | | | | +- card/
- | | | | | +- mocks/
- | | | | | | +- CardReaderAlwaysThrowsIoExceptionMock
- | | | | | | +- ThrowsIoExceptionMock
- | | | | | +- CardExtrasTest
- | | | | | +- CardReaderTest
- | | | | +- controller/
- | | | | | +- mocks/
- | | | | | | +- SanguineControllerMock
- | | | | | +- MachineVersusMachineTest
- | | | | | +- PlayerActionTest
- | | | | | +- SanguineControllerMockIntegrationTest
- | | | | | +- SanguineGuiFrameMock
- | | | | | +- SanguineModelDelegatesProperlyToController
- | | | | | +- SanguinePlayerDelegatesProperlyToController
- | | | | +- model/
- | | | | | +- BoardInputTest
- | | | | | +- DeckCreatorTest
- | | | | | +- SanguineModelTest
- | | | | +- view/
- | | | | | +- SanguineTextualViewTest
- | | | +- strategy/
- | | | | +- mocks/
- | | | | | +- RecordModelMethodCallsMock
- | | | | | +- SanguineOnlyAllowsMovesToRowTwoColThree
- | | | | | +- SanguineOnlyAllowsMovesToRowZeroColZeroMock
- | | | | +- StrategyTests


# Changes for Part 3

- Changes from the previous assignment
  - We made one change to make it so that when we are doing a move, we pass
  implementation of the controller and changing the pubsub within the controller to no longer be generic.
  - We also redid the view. Previously there was a JPanel for each card whose parent panel was the entire hand.
  We redid this to only have a hand panel that draws all the cards. We changed this because we were having trouble
  deselecting a card after a move was made.

# Additions made / Controller

- Pub-Sub implementations
  - We had many implementations of pub sub, some working based off of each other. 
    - In regard to the basic pub-sub that we had in the previous assignment, that has remained the same,
    and we still use the same listeners. 
    - We created a new listener interface called ModelFeaturesListener. The controller implements this interface and 
    listen's for the current player and when the game is over. 
      - Listener methods are called whoseTurn() & gameOver()
    - The second listener interface we created was the PlayerActionListener. The view implemented this interface 
    listen's for when the player makes a move. However, only a machine player actually publishes an event to the view
    and a human player doesn't.
      - Listener method is called moveHasBeenMade()
    
- In regard to getting the information for that move 
  - We utilize the pubsub that we previously had for the previous assignment and will merely use that
        for our human implementation. For our machine, we will reference the strategy and then put that information
        through the view so that it can be interpreted in the same manner as the human actions.
- For the implementation of the view
  - hasMovedBeenMade() Method: When the view receives a notification from the publisher (machine player) it will be given a move and call 
        several of its listener's methods (controller) to imitate a human player. This allows the machine player to 
        make a move
  - showGameOver() Method: When the game is over the controller calls this method which will cause the view to 
      display a game over message with the winner and score or a tie message
  - showInvalidMove() Method: When the controller is given an invalid move it calls this method which makes the view
  display an invalid move message

- Lastly, we have the implementation for our model.
    - notifyListeners() Method: We have an implementation which will tell the listener everytime a player does
    some sort of move (pass, placing a card, etc). That listener will then delegate to the 
    controller, telling it to wait for this player to make a move. Then, we have another listener
    which checks to see if the game is over. So within the same "notify" method, we just check to see
    if the game is over, notify the controller if the game is over, and display a screen with the
    score / player who won.

- Machine and Player implementations who both implement the PlayerActions interface 
  - Within these two classes we have very simple implementations, the human player does nothing, as its
  behavior is already dealt with in the view, and the machine player just calls to our listener that is
  waiting for some sort of move to get the "MoveValues" object to then make the move.
  - Both implementations a getPlayer() method which returns the Enum Player that they represent


- Public Behaviors for our Controller
  - The only public behavior for our controller is a simple method, playGame() which takes in nothing and 
  starts our game.


# Jar File

- Our jar file is located within the src folder. It should be ran within the parent directory for this project
with the given file. An example command would be this, 
  - java -jar f25-hw07-groups-group-The-Best-Group-Ever-dev.jar 5 7 config/red.deck config/blue.deck human human
    - This would create a game with 5 rows, 7 cols, a red deck, a blue deck, and human p1 and human p2
  - java -jar f25-hw07-groups-group-The-Best-Group-Ever-dev.jar 5 7 config/red.deck config/blue.deck human strategy1
    - this would create a game with 5 rows, 7 cols, a red deck, a blue deck, and a human p1 and a first fit p2
  - The way to configure the game is to input the arguments in the following order:
    - Row - First input
    - Col - Second input
    - Red deck path - Third input (we recommend using config/red.deck)
    - Blue deck path - Fourth input (we recommend using config/blue.deck)
    - Player 1 - Fifth input (clarified below)
    - Player 2 - Sixth input (clarified below)
      - Player Inputs are the following
        - "human" -> human input
        - "strategy1" -> first fit algorithm
        - "strategy2" -> maximize row algorithm
  - With this information, move the jar file to the root directory (f25-hw07-groups-group-The-Best-Group-Ever)
  and then run the program in the terminal with the relevant commands you want.
  
