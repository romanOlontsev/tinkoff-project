package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.service.SendMessageService;
import ru.tinkoff.edu.java.scrapper.service.client.BotClient;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpAccessConfig {

    @Bean
    public SendMessageService sendMessageService(@Qualifier("botClientWithTimeout") WebClient webClient) {
        return new BotClient(webClient);
    }
}
