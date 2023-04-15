package ru.tinkoff.edu.java.scrapper.container;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ContainerTest extends IntegrationEnvironment {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

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
    void containerTest_shouldReturnTestRow() {
        String SQL = "SELECT * FROM link_info.chat c " +
                "JOIN link_info.link l on c.chat_id = l.chat_id " +
                "WHERE c.chat_id=333";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();

            assertAll("container start test",
                    () -> assertThat(rs.getLong("chat_id")).isEqualTo(333),
                    () -> assertThat(rs.getString("url")).isEqualTo("Gaga.url")
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
