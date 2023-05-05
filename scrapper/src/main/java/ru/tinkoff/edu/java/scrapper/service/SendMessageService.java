package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateRequest;

public interface SendMessageService {
    void sendMessage(LinkUpdateRequest requestBody);
}
