package ru.tinkoff.edu.java.bot.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.model.response.LinkResponse;
import ru.tinkoff.edu.java.bot.model.response.ListLinksResponse;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ScrapperClient {
    private final WebClient webClient;
    @Value("${scrapper.base-url}")
    private URI baseUrl;

    public Mono<ResponseEntity<ListLinksResponse>> getLinks(Long tgChatId) {
        URI url = URI.create(baseUrl + "/links");

        return webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Tg-Chat-Id", String.valueOf(tgChatId))
                        .retrieve()
                        .toEntity(ListLinksResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> postLinks(Long tgChatId, AddLinkRequest requestBody) {
        URI url = URI.create(baseUrl + "/links");

        return webClient.post()
                        .uri(url)
                        .body(Mono.just(requestBody), AddLinkRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Tg-Chat-Id", String.valueOf(tgChatId))
                        .retrieve()
                        .toEntity(LinkResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> deleteLinks(Long tgChatId, RemoveLinkRequest requestBody) {
        URI url = URI.create(baseUrl + "/links");

        return webClient.method(HttpMethod.DELETE)
                        .uri(url)
                        .body(Mono.just(requestBody), RemoveLinkRequest.class)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Tg-Chat-Id", String.valueOf(tgChatId))
                        .retrieve()
                        .toEntity(LinkResponse.class);
    }

    public Mono<ResponseEntity<Void>> registerChat(Long id) {
        URI url = URI.create(baseUrl + "/tg-chat/" + id);

        return webClient.post()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Void.class);
    }

    public Mono<ResponseEntity<Void>> deleteChat(Long id) {
        URI url = URI.create(baseUrl + "/tg-chat/" + id);

        return webClient.delete()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Void.class);
    }
}
