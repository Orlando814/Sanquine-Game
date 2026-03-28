package sanguine.model.card;

import java.util.List;

/**
 * will represent the interface for a card, giving us all the necessary behaviors.
 */
public interface CardExtras extends Card {

  /**
   * will get the name of the given card.
   *
   * @return the name of the given card as a String.
   */
  String getName();

  /**
   * will get the cost of the given card as an integer.
   *
   * @return the cost of the card as an integer.
   */
  int getCost();

  /**
   * will return the value of the given card that we are referencing.
   *
   * @return the value of the card as an int.
   */
  int getValue();

  /**
   * returns the 2d array that represents the cards like power in essence.
   *
   * @return the 2d array that represents the card.
   */
  List<List<CardInfluence>> getArray();
}
