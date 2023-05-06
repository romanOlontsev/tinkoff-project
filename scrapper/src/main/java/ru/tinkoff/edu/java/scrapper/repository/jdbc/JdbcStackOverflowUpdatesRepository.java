package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.dto.updates.StackOverflowUpdatesDto;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;

@Repository
public class JdbcStackOverflowUpdatesRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcStackOverflowUpdatesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StackOverflowUpdatesDto findStackOverflowUpdatesByLinkId(Long linkId) {
        String query = "SELECT * FROM link_info.stackoverflow_updates WHERE id=?";
        return jdbcTemplate.queryForObject(
            query,
            (rs, rowNum) -> StackOverflowUpdatesDto.builder()
                                                   .id(rs.getLong("id"))
                                                   .answerCount(rs.getInt("answer_count"))
                                                   .isAnswered(rs.getBoolean("is_answered"))
                                                   .build(),
            linkId
        );
    }

    public int updateStackOverflowInfo(Long id, StackOverflowQuestionInfoResponse response) {
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
        String query = "UPDATE link_info.stackoverflow_updates "
            + "SET is_answered = ?, answer_count=? "
            + "WHERE id = ?";
        return jdbcTemplate.update(query, isAnswered, answerCount, id);
    }

    public void addStackOverflowUpdatesByLink(Long linkId) {
        String query = "INSERT INTO link_info.stackoverflow_updates(id) "
            + "VALUES (?)";
        jdbcTemplate.update(query, linkId);
    }
}
