package ru.tinkoff.edu.java.bot.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.response.LinkResponse;
import ru.tinkoff.edu.java.bot.model.response.ListLinksResponse;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ScrapperClient {
    private final WebClient webClient;
    @Value("${scrapper.base-url}")
    private URI baseUrl;

    public Mono<ResponseEntity<ListLinksResponse>> getLinks(String tgChatId) {
        URI url = URI.create(baseUrl + "/links");

        return webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Tg-Chat-Id", tgChatId)
                        .retrieve()
                        .toEntity(ListLinksResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> postLinks(String tgChatId, AddLinkRequest requestBody) {
        URI url = URI.create(baseUrl + "/links");

        return webClient.post()
                        .uri(url)
                        .body(Mono.just(requestBody), AddLinkRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Tg-Chat-Id", tgChatId)
                        .retrieve()
                        .toEntity(LinkResponse.class);
    }
}
