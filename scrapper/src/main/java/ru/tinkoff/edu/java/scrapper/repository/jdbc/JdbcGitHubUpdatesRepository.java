package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.dto.updates.GitHubUpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.response.GitHubRepositoryInfoResponse;

@Repository
public class JdbcGitHubUpdatesRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcGitHubUpdatesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GitHubUpdatesDto findGitHubUpdatesByLinkId(Long linkId) {
        String query = "SELECT * FROM link_info.github_updates WHERE id=?";
        return jdbcTemplate.queryForObject(
            query,
            (rs, rowNum) -> GitHubUpdatesDto.builder()
                                            .id(rs.getLong("id"))
                                            .forksCount(rs.getInt("forks_count"))
                                            .watchers(rs.getInt("watchers"))
                                            .build(),
            linkId
        );
    }

    public int updateGitHubInfo(Long id, GitHubRepositoryInfoResponse response) {
        String query = "UPDATE link_info.github_updates "
            + "SET forks_count = ?, watchers=? "
            + "WHERE id = ?";
        return jdbcTemplate.update(query, response.getForksCount(), response.getWatchers(), id);
    }

    public void addGitHubUpdatesByLink(Long linkId) {
        String query = "INSERT INTO link_info.github_updates(id) "
            + "VALUES (?)";
        jdbcTemplate.update(query, linkId);
    }
}
