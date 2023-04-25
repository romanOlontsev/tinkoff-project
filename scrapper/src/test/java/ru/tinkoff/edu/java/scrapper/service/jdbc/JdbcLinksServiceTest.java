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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.container.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

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
    @Autowired
    private LinkService linkService;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @BeforeEach
    void setUp() {
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
    @Transactional
    @Rollback
    void addLink_shouldThrowBadRequestException() {
        AddLinkRequest request = AddLinkRequest.builder()
                                               .link(URI.create("https://github.com/Gadetych/tinkoff-project/pull/5"))
                                               .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.addLink(1000L, request))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    @Rollback
    void addLink_shouldThrowDataAlreadyExistException() {
        AddLinkRequest addLinkRequest = AddLinkRequest.builder()
                                                      .link(URI.create(
                                                              "https://github.com/Gadetych/tinkoff-project/pull/5"))
                                                      .build();
        linkService.addLink(99999L, addLinkRequest);
        assertAll(
                () -> assertThatThrownBy(() -> linkService.addLink(99999L, addLinkRequest))
                        .isInstanceOf(DataAlreadyExistException.class)
                        .hasMessageContaining("Ссылка: https://github.com/Gadetych/tinkoff-project/pull/5 " +
                                "уже существует у пользователя с id=99999")
        );
    }

    @Test
    @Transactional
    @Rollback
    void addLink_shouldReturnExpectedResponse() {
        String expectedUrl = "https://github.com/Gadetych/tinkoff-project/pull/5";
        LinkResponse response = linkService.addLink(333L, AddLinkRequest.builder()
                                                                        .link(URI.create(expectedUrl))
                                                                        .build());
        LinkResponse expectedResponse = LinkResponse.builder()
                                                    .id(5L)
                                                    .url(URI.create(expectedUrl))
                                                    .build();
        assertAll(
                () -> assertThat(response.getUrl()).isEqualTo(expectedResponse.getUrl()),
                () -> assertThat(response.getId()).isEqualTo(expectedResponse.getId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeLink_shouldThrowBadRequestException() {
        RemoveLinkRequest request = RemoveLinkRequest.builder()
                                                     .link(URI.create(
                                                             "https://github.com/Gadetych/tinkoff-project/pull/5"))
                                                     .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.removeLink(1000L, request))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeLink_shouldThrowDataNotFoundException() {
        RemoveLinkRequest request = RemoveLinkRequest.builder()
                                                     .link(URI.create(
                                                             "https://github.com/Gadetych/tinkoff-project/pull/5"))
                                                     .build();
        assertAll(
                () -> assertThatThrownBy(() -> linkService.removeLink(99999L, request))
                        .isInstanceOf(DataNotFoundException.class)
                        .hasMessageContaining("Ссылка: https://github.com/Gadetych/tinkoff-project/pull/5" +
                                " не существует у пользователя с id=99999")
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeLink_shouldReturnExpectedResponse() {
        String expectedUrl = "https://github.com/Gadetych/tinkoff-project/pull/5";
        linkService.addLink(99999L, AddLinkRequest.builder()
                                                  .link(URI.create(expectedUrl))
                                                  .build());
        LinkResponse response = linkService.removeLink(99999L, RemoveLinkRequest.builder()
                                                                                .link(URI.create(expectedUrl))
                                                                                .build());
        LinkResponse expectedResponse = LinkResponse.builder()
                                                    .id(6L)
                                                    .url(URI.create(expectedUrl))
                                                    .build();
        assertAll(
                () -> assertThat(response.getUrl()).isEqualTo(expectedResponse.getUrl()),
                () -> assertThat(response.getId()).isEqualTo(expectedResponse.getId())
        );

    }

    @Test
    @Transactional
    @Rollback
    void findAllLinksByTgChatId_shouldThrowBadRequestException() {
        assertAll(
                () -> assertThatThrownBy(() -> linkService.findAllLinksByTgChatId(1000L))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Чат с id=1000 не существует")
        );
    }

    @Test
    @Transactional
    @Rollback
    void findAllLinksByTgChatId_shouldReturnExpectedResponse() {
        System.out.println(linkService.findAllLinksByTgChatId(99999L));
        LinkResponse firstExpResponse = linkService
                .addLink(99999L, AddLinkRequest.builder()
                                               .link(URI.create("https://github.com/Gadetych/tinkoff-project/pull/5"))
                                               .build());
        LinkResponse secondExpResponse = linkService
                .addLink(99999L, AddLinkRequest.builder()
                                               .link(URI.create("https://github.com/Gadetych/tinkoff-project/pull/6"))
                                               .build());
        List<LinkResponse> expResponseList = List.of(firstExpResponse, secondExpResponse);
        ListLinksResponse expectedResponse = new ListLinksResponse();
        expectedResponse.setLinks(expResponseList);

        ListLinksResponse response = linkService.findAllLinksByTgChatId(99999L);

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