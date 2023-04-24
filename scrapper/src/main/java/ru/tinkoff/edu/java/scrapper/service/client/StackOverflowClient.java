package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.parser.result.StackOverflowResultRecord;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

@Service
@RequiredArgsConstructor
public class StackOverflowClient {
    @Qualifier("stackOverflowClientWithTimeout")
    private final WebClient webClient;

    public Mono<StackOverflowQuestionInfoResponse> getStackOverflowQuestionInfo(StackOverflowResultRecord questionId) {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/2.3/questions/{id}")
                                .queryParam("order", "desc")
                                .queryParam("sort", "activity")
                                .queryParam("site", "stackoverflow")
                                .build(questionId.getResult()))
                        .retrieve()
                        .bodyToMono(StackOverflowQuestionInfoResponse.class);

    }
}