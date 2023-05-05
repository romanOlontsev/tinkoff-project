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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.container.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class JdbcTgChatServiceTest extends IntegrationEnvironment {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    private TgChatService tgChatService;
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate(
                new DriverManagerDataSource(url, username, password));
        tgChatService = new JdbcTgChatService(
                new JdbcTgChatRepository(jdbcTemplate));

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
    void registerChat_shouldThrowDataAlreadyExistException() {
        assertAll(
                () -> assertThatThrownBy(() -> tgChatService.registerChat(6633L))
                        .isInstanceOf(DataAlreadyExistException.class)
                        .hasMessageContaining("Чат с id=6633 уже существует"));
    }

    @Test
    void registerChat_shouldAddToDb() {
        Long addedTgChatId = 123L;

        TgChatResponse tgChatResponse = tgChatService.registerChat(addedTgChatId);
        Long response = jdbcTemplate.queryForObject("SELECT * FROM link_info.chat WHERE chat_id=?",
                Long.class,
                addedTgChatId);

        assertAll(
                () -> assertThat(response).isNotNull()
                                          .isEqualTo(addedTgChatId),
                () -> assertThat(tgChatResponse).isNotNull()
                                                .extracting("tgChatId")
                                                .isEqualTo(addedTgChatId)
        );
    }

    @Test
    void removeChat_shouldThrowDataNotFoundException() {
        assertAll(
                () -> assertThatThrownBy(() -> tgChatService.removeChat(1000L))
                        .isInstanceOf(DataNotFoundException.class)
                        .hasMessageContaining("Чат с id=1000 не найден"));
    }

    @Test
    void removeChat_shouldReturnExpectedResponse() {
        Long removedChatId = 333L;

        TgChatResponse tgChatResponse = tgChatService.removeChat(removedChatId);
        List<Long> allChatIdInDb = jdbcTemplate.query("SELECT * FROM link_info.chat", (rs, rowNum) ->
                rs.getLong("chat_id"));

        assertAll(
                () -> assertThat(tgChatResponse).isNotNull()
                                                .extracting("tgChatId")
                                                .isEqualTo(removedChatId),
                () -> assertThat(allChatIdInDb).doesNotContain(removedChatId)
        );
    }
}