package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.mapper.ChatMapper;
import ru.tinkoff.edu.java.scrapper.mapper.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {

    @Bean
    public LinkService linkService(JpaLinkRepository jpaLinkRepository,
                                   JpaTgChatRepository jpaTgChatRepository,
                                   LinkMapper linkMapper) {
        return new JpaLinkService(jpaLinkRepository, jpaTgChatRepository, linkMapper);
    }

    @Bean
    public TgChatService tgChatService(JpaTgChatRepository chatRepository,
                                       ChatMapper chatMapper) {
        return new JpaTgChatService(chatRepository, chatMapper);
    }
}
