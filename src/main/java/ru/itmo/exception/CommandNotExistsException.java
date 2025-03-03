package ru.itmo.exception;

public class CommandNotExistsException extends RuntimeException {
  public CommandNotExistsException(String message) {
    super(message);
  }
}
