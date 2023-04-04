package ru.tinkoff.edu.java.bot.service.command.imp;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import ru.tinkoff.edu.java.bot.service.command.Command;

public class UnknownCommand implements Command {
    @Getter
    private static final String UNKNOWN_COMMAND = """
            I don't know this command =(
            check /help""";

    @Override
    public String command() {
        return "";
    }

    @Override
    public String description() {
        return "unknown command";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message()
                                     .chat()
                                     .id(), UNKNOWN_COMMAND);
    }
}
