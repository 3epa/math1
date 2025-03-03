package ru.itmo.manager;

import ru.itmo.command.Command;
import ru.itmo.exception.CommandNotExists;

import java.util.ArrayList;

public class CommandManager {
    private ArrayList<Command> commands;

    public CommandManager(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void execute(String commandName) throws CommandNotExists {

    }
}
