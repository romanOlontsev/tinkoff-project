package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinksService implements LinkService {
    private final LinksRepository linksRepository;

    @Override
    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linksRepository.add(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataAlreadyExistException(
                    "Ссылка: " + response.getUrl() + " уже существует у пользователя с id=" + tgChatId);
        }
        return response;
    }

    @Override
    @Transactional
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        LinkResponse response = linksRepository.remove(tgChatId, request);
        if (response.getId() < 0) {
            throw new DataNotFoundException(
                    "Ссылка: " + response.getUrl() + " не существует у пользователя с id=" + tgChatId);
        }
        return response;
    }

    @Override
    @Transactional
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        return linksRepository.findOneOldestLinksByLastCheckForEachUser();

    }

    @Override
    @Transactional
    public void setLastCheck(Long id) {
        linksRepository.setLastCheck(id);
    }

    @Override
    public void setLastUpdate(Long id, OffsetDateTime update) {
        linksRepository.setLastUpdate(id, update);
    }

    @Override
    @Transactional
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        if (!linksRepository.chatIsExists(tgChatId)) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        return linksRepository.findAll(tgChatId);
    }
}
