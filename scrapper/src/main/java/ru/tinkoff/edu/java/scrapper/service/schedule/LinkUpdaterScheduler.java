package ru.tinkoff.edu.java.scrapper.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.parser.result.ParseResult;
import ru.tinkoff.edu.java.scrapper.dto.LinkWithUpdateDto;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.client.GitHubClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    @Value("${supported-links}")
    private final String[] supportedLinks;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final Parser parser;

    @Override
    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        String gitHubLink = supportedLinks[0];
        String stackOverFlowLink = supportedLinks[1];

        List<LinkWithUpdateDto> allOldestLinksByLastUpdate = linkService.findAllOldestLinksByLastUpdate();

        allOldestLinksByLastUpdate.stream()
                                  .filter(it -> it.getUrl()
                                                  .toString()
                                                  .contains(gitHubLink))
                                  .forEach(it -> {
                                      ParseResult parseResult = parser.checkLink(it.getUrl()
                                                                                   .toString());
                                      GitHubRepositoryInfoResponse response = gitHubClient
                                              .getGitHubRepositoryInfo((GitHubResultRecord) parseResult)
                                              .block();
                                      if (response != null && response.getUpdatedAt()
                                                                      .isAfter())
                                  });


        log.info("Update something");
    }
}
