package ru.tinkoff.edu.java.bot.service.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.configuration.TelegramConfig;
import ru.tinkoff.edu.java.bot.service.command.Command;
import ru.tinkoff.edu.java.bot.service.command.CommandList;
import ru.tinkoff.edu.java.bot.service.command.imp.UnknownCommand;

@Service
public class BotStarter implements Bot {
    private final TelegramBot bot;
    private final CommandList commandList;
    private final Counter counter;

    public BotStarter(TelegramConfig telegramConfig, CommandList commandList, Counter counter) {
        this.bot = new TelegramBot(telegramConfig.token());
        this.commandList = commandList;
        this.counter = counter;
        botCommandInit();
    }

    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Message message = update.message();
            if (message != null) {
                counter.increment();
                SendMessage sendMessage = handleByCommand(update, message);
                bot.execute(sendMessage);
            }
        });
        return 0;
    }

    public SendMessage handleByCommand(Update update, Message message) {
        Command command = commandList.getCommandList()
                                     .stream()
                                     .filter(it -> it.command()
                                                     .equals(message.text()))
                                     .findFirst()
                                     .orElseGet(UnknownCommand::new);
        return command.handle(update);
    }

    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

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
