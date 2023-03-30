package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.parser.result.StackOverflowResultRecord;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

@Service
@RequiredArgsConstructor
public class StackOverflowClient {
    private final WebClient webClient;
    @Value("${stackoverflow.webclient.base-url}")
    private String baseUrl;

    public Mono<StackOverflowQuestionInfoResponse> getStackOverflowQuestionInfo(StackOverflowResultRecord questionId) {
        String url = questionId == null ? String.format(baseUrl, "") : String.format(baseUrl, questionId.getResult());

        return webClient.get()
                        .uri(url)
                        .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                        .retrieve()
                        .bodyToMono(StackOverflowQuestionInfoResponse.class);

    }
}