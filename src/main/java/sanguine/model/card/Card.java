package sanguine.model.card;

/**
 * This represents a general playing card that isn't specific to any game. Only contains a
 * toString() method as that's all the user needs to see.
 */
public interface Card {

  /**
   * Creates a visual representation of the card as a String.
   *
   * @return a String representation of the card.
   */
  String toString();
}
