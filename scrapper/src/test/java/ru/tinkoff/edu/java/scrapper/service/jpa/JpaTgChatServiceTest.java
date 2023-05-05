package ru.tinkoff.edu.java.scrapper.service.jpa;

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
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class JpaTgChatServiceTest extends IntegrationEnvironment {
    @Autowired
    private TgChatService tgChatService;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
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
    void registerChat_shouldThrowDataAlreadyExistException() {
        assertAll(
                () -> assertThatThrownBy(() -> tgChatService.registerChat(99999L))
                        .isInstanceOf(DataAlreadyExistException.class)
                        .hasMessageContaining("Чат с id=99999 уже существует"));
    }

    @Test
    void registerChat_shouldReturnExpectedResponse() {
        Long chatId = 123321L;
        TgChatResponse expectedResponse = TgChatResponse.builder()
                                                        .tgChatId(chatId)
                                                        .build();

        TgChatResponse tgChatResponse = tgChatService.registerChat(chatId);

        assertAll(
                () -> assertThat(tgChatResponse).isNotNull()
                                                .isEqualTo(expectedResponse),
                () -> assertThat(tgChatResponse).extracting("tgChatId")
                                                .isEqualTo(chatId)
        );
    }

    @Test
    void removeChat_shouldThrowDataNotFoundException() {
        assertAll(
                () -> assertThatThrownBy(() -> tgChatService.removeChat(123321L))
                        .isInstanceOf(DataNotFoundException.class)
                        .hasMessageContaining("Чат с id=123321 не найден"));
    }

    @Test
    void removeChat_shouldReturnExpectedResponse() {
        Long chatId = 99999L;
        TgChatResponse expectedResponse = TgChatResponse.builder()
                                                        .tgChatId(chatId)
                                                        .build();

        TgChatResponse tgChatResponse = tgChatService.removeChat(chatId);

        assertAll(
                () -> assertThat(tgChatResponse).isNotNull()
                                                .isEqualTo(expectedResponse),
                () -> assertThat(tgChatResponse).extracting("tgChatId")
                                                .isEqualTo(chatId)
        );
    }
}