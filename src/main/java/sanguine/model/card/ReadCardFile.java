package sanguine.model.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the implementation of reading a Sanguine card from the deck config file.
 * Has several methods to produce specific information about a card such as value, cost, influence,
 * and name.
 */
public class ReadCardFile implements CardReader {
  private final List<String> cards;
  private final Scanner scan;

  /**
   * This is the constructor for this class. It first reads the given file and creates
   * a Scanner on it. Then it initialises the cards field using the createCardList() helper method.
   *
   * @param cardFile is the file that we want to read the card information from.
   * @throws IllegalArgumentException if argument is null or the file cannot be found.
   * @throws IllegalStateException    if an IOException is thrown due to a problem with the append()
   *                                  method.
   */
  public ReadCardFile(File cardFile) throws IllegalArgumentException, IllegalStateException {
    if (cardFile == null) {
      throw new IllegalArgumentException("File cannot be null");
    }
    try {
      FileReader file = new FileReader(cardFile);
      this.scan = new Scanner(file);
      this.cards = this.createCardList();
    } catch (final FileNotFoundException e) {
      throw new IllegalArgumentException("File cannot be found");
    }
  }

  /**
   * This reads the given file and creates a list of String where each item is 6 \n of String
   * representing one card. Does this by adding 6 lines of String from the file to a StringBuilder
   * and keeps track using a counter. Once the counter is divisible by 6 the toString of the
   * StringBuilder is added to the list and reset.
   *
   * @return a list of String representing where each item represents a single card.
   * @throws IllegalStateException if an IOException is thrown due to a problem with the append()
   *                               method.
   */
  private List<String> createCardList() throws IllegalStateException {
    Appendable stringOfCard = new StringBuilder();
    List<String> cards = new ArrayList<>();
    int count = 0;
    while (this.scan.hasNext()) {
      try {
        stringOfCard.append(this.scan.nextLine());
        count++;
        if (count % 6 != 0) {
          stringOfCard.append(System.lineSeparator());
        } else {
          cards.add(stringOfCard.toString());
          stringOfCard = new StringBuilder();
        }
      } catch (final IOException e) {
        throw new IllegalStateException("Bad IO");
      }
    }
    this.scan.close();
    return cards;
  }

  @Override
  public List<String> showCards() {
    return this.cards;
  }

  /**
   * This returns the String representation of the name, cost, or value based on the user input.
   * Does this by splitting the String at the given index of the list of cards by " " and returning
   * specific tokens.
   *
   * @param cardIndex is the index of the specified card starting from index 0.
   * @param type      is the type (name, cost, value) that is to be returned.
   * @return a String of the name, cost, or value of the card.
   * @throws IllegalArgumentException if given argument is less than 0 or greater than the number
   *                                  of cards in the list (starting from 0)
   * @throws IllegalArgumentException if given type isn't name, cost, or value.
   */
  public String showNameValueCost(int cardIndex, String type) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= this.cards.size()) {
      throw new IllegalArgumentException("Card index must be between 0 and " + this.cards.size());
    }
    String card = this.cards.get(cardIndex);
    String[] tokens = card.split(" ");
    switch (type) {
      case "name" -> {
        return tokens[0];
      }
      case "cost" -> {
        return tokens[1];
      }
      case "value" -> {
        return tokens[2].substring(0, 1);
      }
      default -> throw new IllegalArgumentException("type must be: name, cost, or value");
    }
  }

  /**
   * This will take the String representation of a card at the specified index and return a list of 
   * a list of CardInfluence representing the influence grid of a card. Does this by first splitting
   * string by "\n" and then using a double for loop to iterate over each character. Then based on
   * a switch statement the corresponding value is assigned. An empty cell is null.
   *
   * @param cardIndex is the specified index of a card.
   * @return a list of a list of CardInfluence representing the influence grid of the specified 
   *      card.
   * @throws IllegalArgumentException if the given cardIndex is less than 0 or greater than the 
   *      number of cards (starting from 0).
   * @throws IllegalArgumentException if the config file of cards is somehow messed up and contains
   *      a character that isn't 'X', 'C', or 'I' in its influence grid.
   */
  public List<List<CardInfluence>> showInfluence(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= this.cards.size()) {
      throw new IllegalArgumentException("Card index must be between 0 and " + this.cards.size());
    }
    String card = this.cards.get(cardIndex);
    String[] tokens = card.split(System.lineSeparator());
    List<List<CardInfluence>> influences = new ArrayList<>();
    for (int rowIndex = 1; rowIndex < tokens.length; rowIndex++) {
      List<CardInfluence> influenceRow = new ArrayList<>();
      for (int cellIndex = 0; cellIndex < tokens[rowIndex].length(); cellIndex++) {
        switch (tokens[rowIndex].charAt(cellIndex)) {
          case 'X' -> influenceRow.add(null);
          case 'I' -> influenceRow.add(CardInfluence.INFLUENCE);
          case 'C' -> influenceRow.add(CardInfluence.CARD);
          default -> throw new IllegalArgumentException("State of card influence is invalid");
        }
      }
      influences.add(influenceRow);
    }
    return influences;
  }
}
