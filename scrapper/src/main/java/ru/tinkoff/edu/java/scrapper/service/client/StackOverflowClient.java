package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.parser.result.StackOverflowResultRecord;
import ru.tinkoff.edu.java.scrapper.dto.GitHubRepositoryInfoDto;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowQuestionInfoDto;

@Service
@RequiredArgsConstructor
public class StackOverflowClient {
    private final WebClient webClient;
    @Value("${stackoverflow.webclient.base-url}")
    private String baseUrl;

    public Mono<StackOverflowQuestionInfoDto> getStackOverflowQuestionInfo(StackOverflowResultRecord questionId) {
        String url = questionId == null ? baseUrl : "https://api.stackexchange.com/2.3/questions/" +
                questionId.getResult() + "?order=desc&sort=activity&site=stackoverflow";

        return webClient.get()
                        .uri(url)
                        .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                        .retrieve()
                        .bodyToMono(StackOverflowQuestionInfoDto.class);

    }
}