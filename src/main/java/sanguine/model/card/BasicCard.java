package sanguine.model.card;

import java.util.List;
import java.util.Objects;

/**
 * will represent a card for our game of sanguine. We have more hidden behaviors as well as more
 * public ones in our separate classes. This will allow us to hide our interpretation from
 * the user. We will utilize file readers to read the information from the example.deck files.
 */
public class BasicCard implements CardExtras {
  private final String name;
  private final Cost cost;
  private final int value;
  private final List<List<CardInfluence>> influence;

  /**
   * This is the constructor for this class. Initialises the fields to their respective values and
   * does some error checking.
   *
   * @param name      is the name of this card.
   * @param cost      is the cost of this card.
   * @param value     is the value of this card.
   * @param influence is the grid of influence that this card has.
   * @throws IllegalArgumentException if influence or cost is null.
   * @throws IllegalArgumentException if value is less than or equal to 0.
   * @throws IllegalArgumentException if influence grid size isn't a 5x5 grid.
   */
  public BasicCard(String name, Cost cost, int value, List<List<CardInfluence>> influence) throws
      IllegalArgumentException {
    if (cost == null || influence == null || name == null) {
      throw new IllegalArgumentException("Cost, influence, or name cannot be null");
    }
    if (value <= 0) {
      throw new IllegalArgumentException("Value must be greater than 0");
    }
    int cardRowSize = 5;
    int cardColumnSize = 5;
    if (influence.size() != cardRowSize || influence.getFirst().size() != cardColumnSize) {
      throw new IllegalArgumentException("Influence array must be of size 25");
    }
    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influence = influence;
  }

  /**
   * will get the name of the given card.
   *
   * @return the name of the given card as a String.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * will get the cost of the given card as an integer.
   *
   * @return the cost of the card as an integer.
   */
  @Override
  public int getCost() {
    return this.cost.getValue();
  }

  /**
   * will return the value of the given card that we are referencing.
   *
   * @return the value of the card as an int.
   */
  @Override
  public int getValue() {
    return this.value;
  }

  /**
   * returns the 2d array that represents the cards like power in essence.
   *
   * @return the 2d array that represents the card.
   */
  @Override
  public List<List<CardInfluence>> getArray() {
    return this.influence;
  }

  /**
   * will make the card be formatted as a string.
   *
   * @return a String representation of the Card
   */
  @Override
  public String toString() {
    return this.name + System.lineSeparator() + "Cost: " + this.cost.getValue()
        + System.lineSeparator() + "Value: " + this.value + System.lineSeparator()
        + this.influenceToString(this.influence);
  }

  /**
   * Turns a 2D list of CardInfluence back into it's original String representation.
   *
   * @param influence is the 2D list of this card's influence.
   * @return a String version of this cards influence.
   */
  private String influenceToString(List<List<CardInfluence>> influence) {
    StringBuilder strInfluence = new StringBuilder();
    for (List<CardInfluence> row : influence) {
      for (CardInfluence column : row) {
        if (column == null) {
          strInfluence.append("X");
        }
        if (column == CardInfluence.INFLUENCE) {
          strInfluence.append("I");
        }
        if (column == CardInfluence.CARD) {
          strInfluence.append("C");
        }
      }
      strInfluence.append(System.lineSeparator());
    }
    return strInfluence.toString();
  }

  /**
   * Overrides the equals() method which is based on if the value, suit, influence, and cost
   * of a BasicCard is the same as another BasicCard.
   *
   * @param obj the reference object with which to compare.
   * @return a boolean value representing if this entered Object is "equal" to the BasicCard whose
   *     calling this method. Returns false if it isn't.
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof BasicCard)) {
      return false;
    }
    BasicCard that = (BasicCard) obj;
    return this.value == that.value && this.name.equals(that.name) && this.cost.equals(that.cost)
        && this.influence.equals(that.influence);
  }

  /**
   * Overrides the hashCode() method as per required when overriding the equals() method.
   *
   * @return a new hashcode whose value depends on each field of BasicCard.
   */
  public int hashCode() {
    return Objects.hash(this.value, this.name, this.cost, this.influence);
  }
}
