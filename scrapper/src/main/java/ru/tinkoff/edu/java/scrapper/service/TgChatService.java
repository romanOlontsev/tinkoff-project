package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.response.ListTgChatResponse;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;

public interface TgChatService {
    TgChatResponse registerChat(Long tgChatId);

    TgChatResponse removeChat(Long tgChatId);

    ListTgChatResponse findAll();
}
