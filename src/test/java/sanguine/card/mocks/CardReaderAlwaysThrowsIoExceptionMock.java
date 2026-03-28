package sanguine.card.mocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sanguine.model.card.CardInfluence;
import sanguine.model.card.CardReader;

/**
 * This is a mock of the CardReader interface. It's designed to throw a IOException upon
 * initialization to make sure that the class can handle it properly.
 */
public class CardReaderAlwaysThrowsIoExceptionMock implements CardReader {
  private final Scanner scan;

  /**
   * This is the constructor which is intended to call a private method which will throw
   * an IOException error.
   *
   * @param cardFile is the file that contains the deck information.
   */
  public CardReaderAlwaysThrowsIoExceptionMock(File cardFile) {
    if (cardFile == null) {
      throw new IllegalArgumentException("File cannot be null");
    }
    try {
      FileReader file = new FileReader(cardFile);
      this.scan = new Scanner(file);
      this.createCardList();
    } catch (final FileNotFoundException e) {
      throw new IllegalArgumentException("File cannot be found");
    }
  }

  @Override
  public List<String> showCards() {
    return List.of();
  }

  @Override
  public String showNameValueCost(int cardIndex, String type) throws IllegalArgumentException {
    return "";
  }

  @Override
  public List<List<CardInfluence>> showInfluence(int cardIndex) throws IllegalArgumentException {
    return List.of();
  }

  /**
   * Copy of original version, but uses an Appendable mock which will always throw an IOException.
   *
   * @return nothing as it will always error.
   * @throws IllegalStateException when an IOException is thrown by the Appendable mock.
   */
  private List<String> createCardList() throws IllegalStateException {
    Appendable stringOfCard = new ThrowsIoExceptionMock();
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
}
