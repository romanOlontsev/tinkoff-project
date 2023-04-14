package ru.tinkoff.edu.java.scrapper.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.parser.result.ParseResult;
import ru.tinkoff.edu.java.parser.result.StackOverflowResultRecord;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponseDto;
import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.client.BotClient;
import ru.tinkoff.edu.java.scrapper.service.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.service.client.StackOverflowClient;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    @Value("${supported-links}")
    private final String[] supportedLinks;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final Parser parser;

    @Override
    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        List<LinkResponseDto> allOldestLinksByLastUpdate = linkService.findAllOldestLinksByLastCheck();

        getUpdateForGitHubLinks(allOldestLinksByLastUpdate);
        getUpdateForStackOverflowLinks(allOldestLinksByLastUpdate);
    }

    private void getUpdateForGitHubLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        String gitHubLink = supportedLinks[0];
        allOldestLinksByLastUpdate.stream()
                                  .filter(it -> it.getUrl()
                                                  .toString()
                                                  .startsWith(gitHubLink))
                                  .forEach(it -> {
                                      ParseResult parseResult = parser.checkLink(it.getUrl()
                                                                                   .toString());
                                      GitHubRepositoryInfoResponse response = gitHubClient
                                              .getGitHubRepositoryInfo((GitHubResultRecord) parseResult)
                                              .block();
                                      if (response != null && response.getUpdatedAt()
                                                                      .isAfter(it.getLastUpdate())) {
                                          linkService.setLastUpdate(it.getId(), response.getUpdatedAt());
                                          ResponseEntity<Void> messageForBot = sendRequestToBot(it);

                                          log.info("Get update for: id=" +
                                                  it.getId() +
                                                  "---" +
                                                  it.getUrl() +
                                                  "--- bot answer: " +
                                                  (messageForBot != null ? messageForBot.getStatusCode() : "null"));
                                      } else {
                                          linkService.setLastCheck(it.getId());
                                      }
                                  });
    }

    private void getUpdateForStackOverflowLinks(List<LinkResponseDto> allOldestLinksByLastUpdate) {
        String stackOverFlowLink = supportedLinks[1];
        allOldestLinksByLastUpdate.stream()
                                  .filter(it -> it.getUrl()
                                                  .toString()
                                                  .startsWith(stackOverFlowLink))
                                  .forEach(it -> {
                                      ParseResult parseResult = parser.checkLink(it.getUrl()
                                                                                   .toString());
                                      StackOverflowQuestionInfoResponse response = stackOverflowClient
                                              .getStackOverflowQuestionInfo((StackOverflowResultRecord) parseResult)
                                              .block();
                                      if (response != null) {
                                          OffsetDateTime responseLastUpdate =
                                                  response.getItems()
                                                          .stream()
                                                          .map(StackOverflowQuestionInfoResponse.Items::getLastEditDate)
                                                          .findFirst()
                                                          .orElse(it.getLastUpdate());
                                          if (responseLastUpdate.isAfter(it.getLastUpdate())) {
                                              linkService.setLastUpdate(it.getId(), responseLastUpdate);
                                              ResponseEntity<Void> messageForBot = sendRequestToBot(it);
                                              log.info("Get update for: id=" +
                                                      it.getId() +
                                                      " --- " +
                                                      it.getUrl() +
                                                      " --- bot answer: " +
                                                      (messageForBot != null ? messageForBot.getStatusCode() : "null"));
                                          } else {
                                              linkService.setLastCheck(it.getId());
                                          }
                                      }
                                  });
    }

    @Nullable
    private ResponseEntity<Void> sendRequestToBot(LinkResponseDto it) {
        return botClient.postLinks(LinkUpdateRequest.builder()
                                                    .tgChat(it.getId())
                                                    .description("Update available")
                                                    .url(it.getUrl()
                                                           .toString())
                                                    .build())
                        .block();
    }
}