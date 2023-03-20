package ru.tinkoff.edu.java.scrapper.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.scrapper.model.dto.GitHubRepositoryInfoDto;

@Service
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient webClient;
    @Value("${webclient.base-url}")
    private String baseUrl;

    public Mono<GitHubRepositoryInfoDto> getGitHubRepositoryInfo(GitHubResultRecord repository) {
        String url = repository == null? baseUrl : "https://api.github.com/repos/" + repository.getResult();

        return webClient.get()
                        .uri(url)
//                        .header("application/vnd.github+json")
                        .accept(MediaType.valueOf("application/vnd.github+json"))
                        .retrieve()
                        .bodyToMono(GitHubRepositoryInfoDto.class);
    }
}
