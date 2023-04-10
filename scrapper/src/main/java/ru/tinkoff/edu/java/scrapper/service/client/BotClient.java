package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class BotClient {
    @Qualifier("botClientWithTimeout")
    private final WebClient webClient;

    public Mono<ResponseEntity<Void>> postLinks(LinkUpdateRequest requestBody) {
        return webClient.post()
                        .uri(uriBuilder -> uriBuilder.path("/updates")
                                                     .build())
                        .body(Mono.just(requestBody), LinkUpdateRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Void.class);
    }
}
