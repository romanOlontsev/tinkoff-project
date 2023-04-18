package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.response.ListTgChatResponse;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;

public interface TgChatRepository {
    TgChatResponse add(Long tgChatId);

    TgChatResponse remove(Long tgChatId);

    ListTgChatResponse findAll();
}
