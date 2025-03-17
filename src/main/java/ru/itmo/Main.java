package ru.itmo;

import ru.itmo.command.ConsoleCommand;
import ru.itmo.command.FileCommand;
import ru.itmo.command.RandomCommand;
import ru.itmo.exception.CommandNotExistsException;
import ru.itmo.manager.CommandManager;
import ru.itmo.util.PrettyPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager(new ArrayList<>());
        commandManager.addCommand(new ConsoleCommand());
        commandManager.addCommand(new FileCommand());
        commandManager.addCommand(new RandomCommand());
        PrettyPrinter.printMainHeader("Выберите режим, с помощью которого вы хотите вводить матрицу(console, file, random):");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            commandManager.execute(reader.readLine().strip());
        } catch (IOException e) {
            PrettyPrinter.printError("Внутренняя ошибка, попробуйте запустить приложение ещё раз");
        } catch (CommandNotExistsException e) {
            PrettyPrinter.printError("Введённой команды не существует");
        }
    }
}
