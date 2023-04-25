package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    LinkResponse add(Long tgChatId, AddLinkRequest request);

    LinkResponse remove(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAll(Long tgChatId);

    List<LinkResponseDto> findOneOldestLinkByLastCheckForEachUser();

    int updateLastCheck(Long id);

    int updateLastUpdateDate(Long id, OffsetDateTime update);

    Boolean chatIsExists(Long tgChatId);
}
