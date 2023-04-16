package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinksRepository {
    LinkResponse add(Long tgChatId, AddLinkRequest request);

    LinkResponse remove(Long tgChatId, RemoveLinkRequest request);

    ListLinksResponse findAll(Long tgChatId);

    List<LinkResponseDto> findOneOldestLinksByLastCheckForEachUser();

    UpdatesDto findUpdatesByLinkId(Long linkId, String type);

    void setLastCheck(Long id);

    void setLastUpdateDate(Long id, OffsetDateTime update);

    void setGitHubUpdate(Long id, GitHubRepositoryInfoResponse response);

    void setStackOverflowUpdate(Long id, StackOverflowQuestionInfoResponse response);

    Boolean chatIsExists(Long tgChatId);
}
