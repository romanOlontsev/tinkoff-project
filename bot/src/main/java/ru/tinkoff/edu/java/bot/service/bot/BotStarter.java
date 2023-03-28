package ru.tinkoff.edu.java.bot.service.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.configuration.TelegramConfig;
import ru.tinkoff.edu.java.bot.service.command.Command;
import ru.tinkoff.edu.java.bot.service.command.CommandList;
import ru.tinkoff.edu.java.bot.service.command.imp.UnknownCommand;

import java.util.List;

@Service
public class BotStarter implements Bot {
    private final TelegramBot bot;
    private final CommandList commandList;

    public BotStarter(TelegramConfig telegramConfig, CommandList commandList) {
        this.bot = new TelegramBot(telegramConfig.token());
        this.commandList = commandList;
        botCommandInit();
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            Message message = update.message();
            if (message != null) {
                Command command = commandList.getCommandList()
                                             .stream()
                                             .filter(it -> it.command()
                                                             .equals(message.text()))
                                             .findFirst()
                                             .orElseGet(UnknownCommand::new);
                SendMessage sendMessage = command.handle(update);

                bot.execute(sendMessage);
            }
        });
        return 0;
    }

    @Override
    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

    private void botCommandInit() {
        List<Command> commands = commandList.getCommandList();
        BotCommand[] botCommands = commands.stream()
                                           .map(Command::toApiCommand)
                                           .toArray(BotCommand[]::new);
        SetMyCommands request = new SetMyCommands(botCommands);
        bot.execute(request);
    }
}
