package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkUpdatesRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinksService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository,
                                   JdbcLinkUpdatesRepository jdbcLinkUpdatesRepository) {
        return new JdbcLinksService(jdbcLinkRepository, jdbcLinkUpdatesRepository);
    }

    @Bean
    public TgChatService tgChatService(JdbcTgChatRepository tgChatRepository) {
        return new JdbcTgChatService(tgChatRepository);
    }
}
