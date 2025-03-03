package ru.itmo.manager;

import ru.itmo.command.Command;
import ru.itmo.exception.CommandNotExistsException;

import java.util.ArrayList;

public class CommandManager {
    private ArrayList<Command> commands;

    public CommandManager(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void execute(String commandName) throws CommandNotExistsException {
        for (Command command: commands) {
            if (command.getName().equals(commandName)) {
                command.execute();
                return;
            }
        }
        throw new CommandNotExistsException("Command with this name doesn't exist");
    }
}
