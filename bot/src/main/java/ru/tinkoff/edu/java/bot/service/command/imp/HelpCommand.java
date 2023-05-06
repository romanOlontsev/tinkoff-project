package ru.tinkoff.edu.java.bot.service.command.imp;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.Command;

@Component
public class HelpCommand implements Command {

    private static final String HELP_MESSAGE = """
        /start -- register user
        /track -- start tracking a link
        /untrack -- stop tracking a link
        /list -- show list of tracked links
        """;

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "get command list";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message()
                                     .chat()
                                     .id(), HELP_MESSAGE);
    }
}
