package ru.tinkoff.edu.java.scrapper.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.links.GitHubLinkParse;
import ru.tinkoff.edu.java.parser.links.LinkParse;
import ru.tinkoff.edu.java.parser.links.StackOverflowLinkParse;

@Configuration
@EnableScheduling
public class BeanConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public long schedulingIntervalMillis(ApplicationConfig config) {
        return config.scheduler()
                     .interval()
                     .toMillis();
    }

    @Bean
    public Parser linkParser() {
        Parser parser = new Parser();
        parser.setLinks(LinkParse.link(
            new GitHubLinkParse(),
            new StackOverflowLinkParse()
        ));
        return parser;
    }

    @Bean
    public DSLContext dslContext() {
        Settings settings = new Settings();
        settings.setRenderQuotedNames(RenderQuotedNames.NEVER);
        DSLContext context;
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            context = DSL.using(connection, SQLDialect.POSTGRES, settings);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return context;
    }
}
