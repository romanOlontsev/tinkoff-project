package ru.tinkoff.edu.java.bot.service.command;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.imp.HelpCommand;
import ru.tinkoff.edu.java.bot.service.command.imp.ListCommand;
import ru.tinkoff.edu.java.bot.service.command.imp.StartCommand;
import ru.tinkoff.edu.java.bot.service.command.imp.TrackCommand;
import ru.tinkoff.edu.java.bot.service.command.imp.UntrackCommand;

@Component
public class CommandList {

    private final List<Command> commands;

    public CommandList(
        HelpCommand helpCommand,
        StartCommand startCommand,
        TrackCommand trackCommand,
        UntrackCommand untrackCommand,
        ListCommand listCommand
    ) {
        commands = List.of(helpCommand, startCommand, trackCommand, untrackCommand, listCommand);
    }

    public List<Command> getCommandList() {
        return commands;
    }
}
