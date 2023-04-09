package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinksRepository;

@Service
@RequiredArgsConstructor
public class LinksService {

    private final LinksRepository linksRepository;

    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        return linksRepository.add(tgChatId, request);
    }

    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        return linksRepository.remove(tgChatId, request);
    }
}
