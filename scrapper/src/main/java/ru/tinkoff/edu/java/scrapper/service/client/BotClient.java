package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.SendMessageService;

//@Service
@RequiredArgsConstructor
public class BotClient implements SendMessageService {
    @Qualifier("botClientWithTimeout")
    private final WebClient webClient;

    public void sendMessage(LinkUpdateRequest requestBody) {
        webClient.post()
                 .uri(uriBuilder -> uriBuilder.path("/updates")
                                              .build())
                 .body(Mono.just(requestBody), LinkUpdateRequest.class)
                 .accept(MediaType.APPLICATION_JSON)
                 .retrieve()
                 .toEntity(Void.class)
                 .subscribe();
    }
}
