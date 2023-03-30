package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;

@Service
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient webClient;
    @Value("${github.webclient.base-url}")
    private String baseUrl;

    public Mono<GitHubRepositoryInfoResponse> getGitHubRepositoryInfo(GitHubResultRecord repository) {
        String url = repository == null ?
                String.join("", baseUrl, "romanOlontsev/ticket-service") :
                String.join("", baseUrl, repository.getResult());
        return webClient.get()
                        .uri(url)
                        .accept(MediaType.valueOf("application/vnd.github+json"))
                        .retrieve()
                        .bodyToMono(GitHubRepositoryInfoResponse.class);
    }
}
