package ru.tinkoff.edu.java.scrapper.repository.imp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinksRepository;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class LinksRepositoryImpl implements LinksRepository {

    private final JdbcTemplate jdbcTemplate;

    public LinksRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public LinkResponse add(Long tgChatId, AddLinkRequest request) {
        String query = "INSERT INTO link_info.link(url, chat_id) " +
                "SELECT ?,? " +
                "WHERE NOT EXISTS(" +
                "SELECT url FROM link_info.link WHERE chat_id=? AND url=?)";

        String url = request.getLink()
                            .toString();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, url);
            ps.setLong(2, tgChatId);
            ps.setLong(3, tgChatId);
            ps.setString(4, url);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long rowId = key == null ? -1L : key.longValue();

        return LinkResponse.builder()
                           .id(rowId)
                           .url(request.getLink())
                           .build();
    }

    @Override
    public LinkResponse remove(Long tgChatId, RemoveLinkRequest request) {
        String query = "DELETE FROM link_info.link " +
                "WHERE chat_id=? AND url=?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, tgChatId);
            ps.setString(2, request.getLink()
                                   .toString());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        Long rowId = key == null ? -1L : key.longValue();

        return LinkResponse.builder()
                           .id(rowId)
                           .url(request.getLink())
                           .build();
    }

    @Override
    public ListLinksResponse findAll(Long tgChatId) {
        String query = "SELECT * FROM link_info.link " +
                "WHERE chat_id=?";

        ListLinksResponse listLinksResponse = new ListLinksResponse();
        List<LinkResponse> responseList = jdbcTemplate.query(
                query,
                (rs, rowNum) -> LinkResponse.builder()
                                            .id(rs.getLong("id"))
                                            .url(URI.create(rs.getString("url")))
                                            .build(),
                tgChatId);
        listLinksResponse.setLinks(responseList);
        return listLinksResponse;
    }

    @Override
    public ListLinksResponse findAllOrderByLastUpdate() {
        String query = "SELECT l1.*" +
                "FROM link_info.link l1" +
                "WHERE l1.id = (SELECT l2.id" +
                "               FROM link_info.link l2" +
                "               WHERE l2.chat_id = l1.chat_id" +
                "               ORDER BY l2.last_update" +
                "               LIMIT 1);";

        ListLinksResponse listLinksResponse = new ListLinksResponse();
        List<LinkResponse> responseList = jdbcTemplate.query(
                query,
                (rs, rowNum) -> LinkResponse.builder()
                                            .id(rs.getLong("id"))
                                            .url(URI.create(rs.getString("url")))
                                            .build());
        listLinksResponse.setLinks(responseList);
        return listLinksResponse;
    }


    @Override
    public Boolean chatIsExists(Long tgChatId) {
        String query = "SELECT EXISTS(SELECT TRUE FROM link_info.link WHERE chat_id=?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, tgChatId);
    }
}
