package ru.tinkoff.edu.java.bot.service.command.imp;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.Command;

@Component
public class TrackCommand implements Command {
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "start tracking a link";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message()
                                     .chat()
                                     .id(), "start tracking");
    }
}
