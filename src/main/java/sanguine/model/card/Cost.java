package sanguine.model.card;

/**
 * This represents the cost of a Sanguine card. The cost of the card can either be 1, 2, or 3.
 */
public enum Cost {
  ONE(1),
  TWO(2),
  THREE(3);

  private final int cost;

  /**
   * This is the constructor for this class. This initializes the fields to their respective values
   *
   * @param cost is an int representing the cost.
   */
  Cost(int cost) {
    this.cost = cost;
  }

  /**
   * will return the value of the cost of the card itself.
   *
   * @return the value of the cost itself from the enum.
   */
  public int getValue() {
    return this.cost;
  }

  /**
   * general method to get the cost, made static so that we can reference it in a static manner.
   *
   * @param value the value that we are trying to fetch so we can create the new Cost.
   * @return the new Cost with the relevant and appropriate value.
   */
  public static Cost getCost(int value) {
    switch (value) {
      case 1 -> {
        return Cost.ONE;
      }
      case 2 -> {
        return Cost.TWO;
      }
      case 3 -> {
        return Cost.THREE;
      }
      default -> throw new IllegalArgumentException("Cost must be either 1, 2, or 3");
    }
  }
}
