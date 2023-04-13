package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.LinkWithUpdateDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

import java.util.List;

public interface LinkService {
    LinkResponse addLink(Long tgChatId, AddLinkRequest request);

    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAllLinksByTgChatId(Long tgChatId);

    List<LinkWithUpdateDto> findAllOldestLinksByLastUpdate();

}
