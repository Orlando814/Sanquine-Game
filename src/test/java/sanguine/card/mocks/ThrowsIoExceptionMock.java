package sanguine.card.mocks;

import java.io.IOException;

/**
 * This is a mock of the Appendable class which will always throw an IOException. Intended to use
 * to make sure that any class which uses an Appendable can handle this IOException properly.
 */
public class ThrowsIoExceptionMock implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
