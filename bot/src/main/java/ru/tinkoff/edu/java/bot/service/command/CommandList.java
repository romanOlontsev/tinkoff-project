package ru.tinkoff.edu.java.bot.service.command;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.imp.*;

import java.util.List;

@Component
public class CommandList {

    private final List<Command> commands;

    public CommandList(HelpCommand helpCommand,
                       StartCommand startCommand,
                       TrackCommand trackCommand,
                       UntrackCommand untrackCommand,
                       ListCommand listCommand) {
        commands = List.of(helpCommand, startCommand, trackCommand, untrackCommand, listCommand);
    }

    public List<Command> getCommandList() {
        return commands;
    }
}
