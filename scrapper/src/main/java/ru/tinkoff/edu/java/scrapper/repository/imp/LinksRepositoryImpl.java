package ru.tinkoff.edu.java.scrapper.repository.imp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinksRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class LinksRepositoryImpl implements LinksRepository {

    private final JdbcTemplate jdbcTemplate;

    public LinksRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public LinkResponse add(Long tgChatId, AddLinkRequest request) {
//        String query = "INSERT INTO link_info.link(url, chat_id) VALUES (?, ?)";
        String query = "INSERT INTO link_info.link(url, chat_id) " +
                "SELECT ?,? " +
                "WHERE NOT EXISTS(" +
                "SELECT url FROM link_info.link WHERE chat_id=? AND url=?)";

        String url = request.getLink()
                            .toString();


//        jdbcTemplate.update(query, url, tgChatId, tgChatId, url);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, url);
            ps.setLong(2, tgChatId);
            ps.setLong(3, tgChatId);
            ps.setString(4, url);
            return ps;
        }, keyHolder);

        Long rowId = keyHolder.getKey().longValue();

        return LinkResponse.builder()
                           .id(1L)
                           .url(request.getLink())
                           .build();

    }

    @Override
    public LinkResponse remove(RemoveLinkRequest request) {
        return null;
    }

    @Override
    public ListLinksResponse findAll(Long tgChatId) {
        return null;
    }
}
