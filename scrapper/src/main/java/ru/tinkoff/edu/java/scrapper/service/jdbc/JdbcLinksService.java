package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinkUpdatesRepository;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinksService implements LinkService {
    private final LinkRepository linkRepository;
    private final LinkUpdatesRepository linkUpdatesRepository;

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
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        return linkRepository.findOneOldestLinksByLastCheckForEachUser();

    }

    @Override
    public UpdatesDto findUpdatesByLinkIdAndLinkType(Long linkId, String type) {
        return linkUpdatesRepository.findUpdatesByLinkId(linkId, type);
    }

    @Override
    @Transactional
    public void setLastCheck(Long id) {
        linkRepository.setLastCheck(id);
    }

    @Override
    @Transactional
    public void setGitHubLastUpdate(Long id, GitHubRepositoryInfoResponse response) {
        linkRepository.setLastUpdateDate(id, response.getUpdatedAt());
        linkUpdatesRepository.setGitHubUpdate(id, response);
    }

    @Override
    @Transactional
    public void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response) {
        OffsetDateTime lastUpdate = response.getItems()
                                            .stream()
                                            .map(StackOverflowQuestionInfoResponse.Items::getLastActivityDate)
                                            .findFirst()
                                            .orElse(null);
        linkRepository.setLastUpdateDate(id, lastUpdate);
        linkUpdatesRepository.setStackOverflowUpdate(id, response);
    }

    @Override
    @Transactional
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        if (!linkRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        return linkRepository.findAll(tgChatId);
    }
}
