package ru.tinkoff.edu.java.scrapper.service.jdbc;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.tinkoff.edu.java.scrapper.container.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.repository.imp.LinksRepositoryImpl;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class JdbcLinksServiceTest extends IntegrationEnvironment {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private LinkService linkService;

    private TgChatService chatService;

    @BeforeEach
    void setUp() {
        linkService = new JdbcLinksService(
                new LinksRepositoryImpl(
                        new JdbcTemplate(
                                new DriverManagerDataSource(url, username, password))));

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Database database = DatabaseFactory.getInstance()
                                                .findCorrectDatabaseImplementation(new JdbcConnection(connection))) {
            Liquibase liquibase = new liquibase.Liquibase("master.xml",
                    new DirectoryResourceAccessor(new File("").toPath()
                                                              .toAbsolutePath()
                                                              .getParent()
                                                              .resolve("migrations")), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException | SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addLink_shouldThrowBadRequestException() {
        AddLinkRequest request = AddLinkRequest.builder()
                                               .link(URI.create("some.url"))
                                               .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.addLink(1000L, request))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    void addLink_shouldThrowDataAlreadyExistException() {
        AddLinkRequest request = AddLinkRequest.builder()
                                               .link(URI.create("Gaga.url"))
                                               .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.addLink(333L, request))
                        .isInstanceOf(DataAlreadyExistException.class)
                        .hasMessageContaining("Ссылка: Gaga.url уже существует у пользователя с id=333")
        );
    }

    @Test
    void addLink_shouldReturnExpectedResponse() {
        String expectedUrl = "some.url";
        LinkResponse expectedResponse = LinkResponse.builder()
                                                    .id(4L)
                                                    .url(URI.create(expectedUrl))
                                                    .build();
        LinkResponse response = linkService.addLink(6633L, AddLinkRequest.builder()
                                                                        .link(URI.create(expectedUrl))
                                                                        .build());
        assertAll(
                () -> assertThat(response.getUrl()).isEqualTo(expectedResponse.getUrl()),
                () -> assertThat(response.getId()).isEqualTo(expectedResponse.getId())
        );
    }

    @Test
    void removeLink_shouldThrowBadRequestException() {
        RemoveLinkRequest request = RemoveLinkRequest.builder()
                                                     .link(URI.create("some.url"))
                                                     .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.removeLink(1000L, request))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    void removeLink_shouldThrowDataNotFoundException() {
        RemoveLinkRequest request = RemoveLinkRequest.builder()
                                                     .link(URI.create("some.url"))
                                                     .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.removeLink(333L, request))
                        .isInstanceOf(DataNotFoundException.class)
                        .hasMessageContaining("Ссылка: some.url не существует у пользователя с id=333")
        );
    }

    @Test
    void removeLink_shouldReturnExpectedResponse() {
        String expectedUrl = "some.url";
        LinkResponse expectedResponse = LinkResponse.builder()
                                                    .id(4L)
                                                    .url(URI.create(expectedUrl))
                                                    .build();
        LinkResponse response = linkService.removeLink(6633L, RemoveLinkRequest.builder()
                                                                              .link(URI.create(expectedUrl))
                                                                              .build());
        assertAll(
                () -> assertThat(response.getUrl()).isEqualTo(expectedResponse.getUrl()),
                () -> assertThat(response.getId()).isEqualTo(expectedResponse.getId())
        );

    }

    @Test
    void findAllLinksByTgChatId_shouldThrowBadRequestException() {
        assertAll(
                () -> assertThatThrownBy(() -> linkService.findAllLinksByTgChatId(1000L))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    void findAllLinksByTgChatId_shouldReturnExpectedResponse() {
        LinkResponse firstExpResponse = LinkResponse.builder()
                                                    .id(2L)
                                                    .url(URI.create("first.url"))
                                                    .build();
        LinkResponse secondExpResponse = LinkResponse.builder()
                                                     .id(3L)
                                                     .url(URI.create("second.url"))
                                                     .build();
        List<LinkResponse> expResponseList = List.of(firstExpResponse, secondExpResponse);
        ListLinksResponse expectedResponse = new ListLinksResponse();
        expectedResponse.setLinks(expResponseList);

//        linkService.addLink(6633L, AddLinkRequest.builder()
//                                                 .link(URI.create("first.url"))
//                                                 .build());
//        linkService.addLink(6633L, AddLinkRequest.builder()
//                                                 .link(URI.create("second.url"))
//                                                 .build());

        ListLinksResponse response = linkService.findAllLinksByTgChatId(6633L);

        assertAll(
                () -> assertThat(response.getLinks()
                                         .get(0)
                                         .getUrl()).isEqualTo(firstExpResponse.getUrl()),
                () -> assertThat(response.getLinks()
                                         .get(1)
                                         .getId()).isEqualTo(secondExpResponse.getId()),
                () -> assertThat(response.getSize()).isEqualTo(expectedResponse.getSize())
        );
    }
}