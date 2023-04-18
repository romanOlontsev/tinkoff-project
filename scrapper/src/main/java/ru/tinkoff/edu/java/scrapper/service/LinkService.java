package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

import java.util.List;

public interface LinkService {
    LinkResponse addLink(Long tgChatId, AddLinkRequest request);

    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request);

    void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response);

    ListLinksResponse findAllLinksByTgChatId(Long tgChatId);

    List<LinkResponseDto> findAllOldestLinksByLastCheck();

    UpdatesDto findUpdatesByLinkIdAndLinkType(Long linkId, String type);

    void setLastCheck(Long id);

    void setGitHubLastUpdate(Long id, GitHubRepositoryInfoResponse update);
}
