package ru.tinkoff.edu.java.bot.service.command.imp;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.LinkService;
import ru.tinkoff.edu.java.bot.service.command.Command;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListCommand implements Command {
    private final LinkService linkService;

    public ListCommand(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "show list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        String answer = "Link list is empty";

//        linkService.setLinkList(List.of("Saks", "Gas"));
        if (linkService.getLinkList() != null && !linkService.getLinkList()
                                                             .isEmpty()) {
            List<String> linkList = linkService.getLinkList();
            answer = String.join("\n", linkList);
        }

        return new SendMessage(update.message()
                                     .chat()
                                     .id(), answer);
    }
}
