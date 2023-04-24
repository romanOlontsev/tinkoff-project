package ru.tinkoff.edu.java.scrapper.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.service.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;

import java.net.URI;

@RestController
@AllArgsConstructor
public class GitHubTestController {
    private GitHubClient gitHubClient;
    private final JooqLinkService jooqLinkService;

    @GetMapping("/github/{user}/{repo}")
    public GitHubRepositoryInfoResponse getRepoInfo(@PathVariable String user, @PathVariable String repo) {
        return gitHubClient.getGitHubRepositoryInfo(new GitHubResultRecord(user, repo))
                           .block();
    }

    @GetMapping("/github")
    public GitHubRepositoryInfoResponse getRepoInfoWithNull() {
        return gitHubClient.getGitHubRepositoryInfo(null)
                           .block();
    }

    @GetMapping("/some/{id}")
    public LinkResponse getSome(@PathVariable Long id) {
        return jooqLinkService.addLink(id, new AddLinkRequest(URI.create("SOME"), "github"));
    }


}
