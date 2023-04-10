package ru.tinkoff.edu.java.scrapper.repository.imp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.response.ListTgChatResponse;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TgChatRepositoryImpl implements TgChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public TgChatRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TgChatResponse add(Long tgChatId) {
        String query = "INSERT INTO link_info.chat (chat_id) " +
                "SELECT ? " +
                "WHERE NOT EXISTS(" +
                "SELECT chat_id FROM link_info.chat WHERE chat_id=?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"chat_id"});
            ps.setLong(1, tgChatId);
            ps.setLong(2, tgChatId);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long rowId = key == null ? -1L : key.longValue();

        return TgChatResponse.builder()
                             .tgChatId(rowId)
                             .build();
    }

    @Override
    public TgChatResponse remove(Long tgChatId) {
        String query = "DELETE FROM link_info.chat " +
                "WHERE chat_id=?";

        int deletedRows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"chat_id"});
            ps.setLong(1, tgChatId);
            return ps;
        });
        Long rowId = deletedRows == 0 ? -1L : tgChatId;

        return TgChatResponse.builder()
                             .tgChatId(rowId)
                             .build();
    }

    @Override
    public ListTgChatResponse findAll() {
        String query = "SELECT * FROM link_info.chat";

        ListTgChatResponse tgChatResponse = new ListTgChatResponse();
        List<TgChatResponse> responseList = jdbcTemplate.query(
                query,
                (rs, rowNum) -> TgChatResponse.builder()
                                              .tgChatId(rs.getLong("chat_id"))
                                              .build());
        tgChatResponse.setTgChatList(responseList);
        return tgChatResponse;
    }
}
