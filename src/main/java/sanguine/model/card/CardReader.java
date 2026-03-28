package sanguine.model.card;

import java.util.List;

/**
 * This class deals with reading the deck configuration file. Intended to assist in the creation of
 * a deck of Sanguine cards.
 */
public interface CardReader {

  /**
   * Shows the current list of cards in their String representation directly from the file.
   *
   * @return a List of String where each item contains 6 \n lines of String representing a single
   *      card.
   */
  List<String> showCards();

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
  String showNameValueCost(int cardIndex, String type) throws IllegalArgumentException;

  /**
   * This will take the String representation of a card at the specified index and return a list of
   * a list of CardInfluence representing the influence grid of a card. Does this by first splitting
   * string by "\n" and then using a double for loop to iterate over each character. Then based on
   * a switch statement the corresponding value is assigned. An empty cell is null.
   *
   * @param cardIndex is the specified index of a card.
   * @return a list of a list of CardInfluence representing the influence grid of the specified
   *        card.
   * @throws IllegalArgumentException if the given cardIndex is less than 0 or greater than the
   *        number of cards (starting from 0).
   * @throws IllegalArgumentException if the config file of cards is somehow messed up and contains
   *        a character that isn't 'X', 'C', or 'I' in its influence grid.
   */
  List<List<CardInfluence>> showInfluence(int cardIndex) throws IllegalArgumentException;
}
