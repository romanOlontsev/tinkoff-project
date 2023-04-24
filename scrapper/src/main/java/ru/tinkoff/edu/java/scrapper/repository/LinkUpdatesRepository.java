package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

public interface LinkUpdatesRepository {

    UpdatesDto findUpdatesByLinkId(Long linkId, String type);

    void setGitHubUpdate(Long id, GitHubRepositoryInfoResponse response);

    void setStackOverflowUpdate(Long id, StackOverflowQuestionInfoResponse response);
}
