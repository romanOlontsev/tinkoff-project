package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("gitHubClientWithTimeout")
    private final WebClient webClient;

    public Mono<GitHubRepositoryInfoResponse> getGitHubRepositoryInfo(GitHubResultRecord repository) {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/repos/{user}/{repo}")
                                                     .build(repository.userName(), repository.repository()))
//                        .accept(MediaType.valueOf("application/vnd.github+json"))
                        .retrieve()
                        .bodyToMono(GitHubRepositoryInfoResponse.class);
    }
}
