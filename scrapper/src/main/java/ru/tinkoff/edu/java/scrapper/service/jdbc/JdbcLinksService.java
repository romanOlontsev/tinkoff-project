package ru.tinkoff.edu.java.scrapper.service.jdbc;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.EntityUpdateException;
import ru.tinkoff.edu.java.scrapper.model.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.model.dto.updates.GitHubUpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.dto.updates.StackOverflowUpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcGitHubUpdatesRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcStackOverflowUpdatesRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JdbcLinksService implements LinkService {
    private final LinkRepository linkRepository;
    private final JdbcGitHubUpdatesRepository gitHubUpdatesRepository;
    private final JdbcStackOverflowUpdatesRepository stackOverflowUpdatesRepository;

    @Override
    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        if (!linkRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linkRepository.add(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataAlreadyExistException(
                "Ссылка: " + response.getUrl() + " уже существует у пользователя с id=" + tgChatId);
        }
        String type = request.getLink()
                             .getHost()
                             .split("\\.")[0];
        addUpdatesByLinkType(type, response.getId());
        return response;
    }

    @Override
    @Transactional
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        if (!linkRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linkRepository.remove(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataNotFoundException(
                "Ссылка: " + response.getUrl() + " не существует у пользователя с id=" + tgChatId);
        }
        return response;
    }

    @Override
    @Transactional
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        if (!linkRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        return linkRepository.findAll(tgChatId);
    }

    @Override
    @Transactional
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        return linkRepository.findOneOldestLinkByLastCheckForEachUser();

    }

    @Override
    public GitHubUpdatesDto findGitHubUpdatesByLinkId(Long linkId) {
        return gitHubUpdatesRepository.findGitHubUpdatesByLinkId(linkId);
    }

    @Override
    public StackOverflowUpdatesDto findStackOverflowUpdatesByLinkId(Long linkId) {
        return stackOverflowUpdatesRepository.findStackOverflowUpdatesByLinkId(linkId);
    }

    @Override
    @Transactional
    public void setLastCheck(Long id) {
        int lastCheckCount = linkRepository.updateLastCheck(id);
        if (lastCheckCount == 0) {
            throw new EntityUpdateException("Last check is not set for id=" + id);
        }
    }

    @Override
    @Transactional
    public void updateGitHubLastUpdateDate(Long id, GitHubRepositoryInfoResponse response) {
        int lastUpdateDateCount = linkRepository.updateLastUpdateDate(id, response.getUpdatedAt());
        if (lastUpdateDateCount == 0) {
            throw new EntityUpdateException("Last update is not set for id=" + id);
        }
        int gitHubUpdateCount = gitHubUpdatesRepository.updateGitHubInfo(id, response);
        if (gitHubUpdateCount == 0) {
            throw new EntityUpdateException("Last update is not set for id=" + id);
        }
    }

    @Override
    @Transactional
    public void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response) {
        OffsetDateTime lastUpdate = response.getItems()
                                            .stream()
                                            .map(StackOverflowQuestionInfoResponse.Items::getLastActivityDate)
                                            .findFirst()
                                            .orElse(null);
        int lastUpdateDateCount = linkRepository.updateLastUpdateDate(id, lastUpdate);
        if (lastUpdateDateCount == 0) {
            throw new EntityUpdateException("Last update is not set for id=" + id);
        }
        int stackOverflowUpdateCount = stackOverflowUpdatesRepository.updateStackOverflowInfo(id, response);
        if (stackOverflowUpdateCount == 0) {
            throw new EntityUpdateException("Last update is not set for id=" + id);
        }
    }

    private void addUpdatesByLinkType(String host, Long linkId) {
        switch (host) {
            case "github" -> gitHubUpdatesRepository.addGitHubUpdatesByLink(linkId);
            case "stackoverflow" -> stackOverflowUpdatesRepository.addStackOverflowUpdatesByLink(linkId);
            default -> throw new DataNotFoundException("Updates for link with id=" + linkId + " not found");
        }
    }
}
