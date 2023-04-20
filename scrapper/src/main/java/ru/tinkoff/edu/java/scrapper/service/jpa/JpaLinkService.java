package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.model.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.model.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaTgChatRepository chatRepository;
    private final LinkMapper linkMapper;

    @Override
    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        chatRepository.findById(tgChatId)
                      .orElseThrow(() -> new BadRequestException("Чат с id=" + tgChatId + " не найден"));
        Chat buildChat = Chat.builder()
                             .tgChatId(tgChatId)
                             .build();
        linkRepository.findByUrlAndChatId(request.getLink()
                                                 .toString(), buildChat)
                      .ifPresent(it -> {
                          throw new DataAlreadyExistException(
                                  "Ссылка: " + request.getLink()
                                                      .toString() + " уже существует у пользователя с id=" +
                                          tgChatId);
                      });
        Link linkToSave = linkMapper.addLinkRequestToLink(request);
        linkToSave.setChatId(buildChat);
        Link savedLink = linkRepository.save(linkToSave);
        return linkMapper.linkToLinkResponse(savedLink);
    }

    @Override
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        return null;
    }

    @Override
    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        return null;
    }

    @Override
    public List<LinkResponseDto> findAllOldestLinksByLastCheck() {
        return null;
    }

    @Override
    public UpdatesDto findUpdatesByLinkIdAndLinkType(Long linkId, String type) {
        return null;
    }

    @Override
    public void setLastCheck(Long id) {

    }

    @Override
    public void setStackOverflowLastUpdate(Long id, StackOverflowQuestionInfoResponse response) {

    }

    @Override
    public void setGitHubLastUpdate(Long id, GitHubRepositoryInfoResponse update) {

    }
}
