package ru.itmo.exception;

public class CommandNotExists extends RuntimeException {
  public CommandNotExists(String message) {
    super(message);
  }
}
