package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.bot.model.request.LinkUpdateRequest;

public interface UpdateService {
    public void receiver(LinkUpdateRequest update);
}
