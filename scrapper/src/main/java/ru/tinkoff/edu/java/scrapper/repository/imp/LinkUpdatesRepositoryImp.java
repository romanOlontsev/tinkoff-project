package ru.tinkoff.edu.java.scrapper.repository.imp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;
import ru.tinkoff.edu.java.scrapper.dto.updates.GitHubUpdatesDto;
import ru.tinkoff.edu.java.scrapper.dto.updates.StackOverflowUpdatesDto;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinkUpdatesRepository;

@Repository
public class LinkUpdatesRepositoryImp implements LinkUpdatesRepository {
    private final JdbcTemplate jdbcTemplate;

    public LinkUpdatesRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UpdatesDto findUpdatesByLinkId(Long linkId, String type) {
        String query;
        switch (type) {
            case "github" -> {
                query = "SELECT * FROM link_info.github_updates WHERE id=?";
                return jdbcTemplate.queryForObject(
                        query,
                        (rs, rowNum) -> GitHubUpdatesDto.builder()
                                                        .id(rs.getLong("id"))
                                                        .forksCount(rs.getInt("forks_count"))
                                                        .watchers(rs.getInt("watchers"))
                                                        .build(),
                        linkId);
            }
            case "stack" -> {
                query = "SELECT * FROM link_info.stackoverflow_updates WHERE id=?";
                return jdbcTemplate.queryForObject(
                        query,
                        (rs, rowNum) -> StackOverflowUpdatesDto.builder()
                                                               .id(rs.getLong("id"))
                                                               .answerCount(rs.getInt("answer_count"))
                                                               .isAnswered(rs.getBoolean("is_answered"))
                                                               .build(),
                        linkId);
            }
            default -> throw new DataNotFoundException("Updates for link with id=" + linkId + " not found");
        }
    }

    @Override
    public void setGitHubUpdate(Long id, GitHubRepositoryInfoResponse response) {
        String query = "UPDATE link_info.github_updates " +
                "SET forks_count = ?, watchers=? " +
                "WHERE id = ?";
        jdbcTemplate.update(query, response.getForksCount(), response.getWatchers(), id);
    }

    @Override
    public void setStackOverflowUpdate(Long id, StackOverflowQuestionInfoResponse response) {
        Boolean isAnswered = response.getItems()
                                     .stream()
                                     .map(StackOverflowQuestionInfoResponse.Items::isAnswered)
                                     .findFirst()
                                     .orElse(null);
        Integer answerCount = response.getItems()
                                      .stream()
                                      .map(StackOverflowQuestionInfoResponse.Items::getAnswerCount)
                                      .findFirst()
                                      .orElse(null);
        String query = "UPDATE link_info.stackoverflow_updates " +
                "SET is_answered = ?, answer_count=? " +
                "WHERE id = ?";
        jdbcTemplate.update(query, isAnswered, answerCount, id);
    }


}
