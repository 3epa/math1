package ru.itmo.exception;

public class CommandNotExistsException extends Exception {
  public CommandNotExistsException(String message) {
    super(message);
  }
}
