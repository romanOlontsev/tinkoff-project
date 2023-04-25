package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.*;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinksService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository,
                                   JdbcGitHubUpdatesRepository jdbcGitHubUpdatesRepository,
                                   JdbcStackOverflowUpdatesRepository jdbcStackOverflowUpdatesRepository) {
        return new JdbcLinksService(jdbcLinkRepository, jdbcGitHubUpdatesRepository, jdbcStackOverflowUpdatesRepository);
    }

    @Bean
    public TgChatService tgChatService(JdbcTgChatRepository tgChatRepository) {
        return new JdbcTgChatService(tgChatRepository);
    }
}
