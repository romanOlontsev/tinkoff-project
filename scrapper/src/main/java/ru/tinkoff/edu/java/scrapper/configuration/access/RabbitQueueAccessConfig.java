package ru.tinkoff.edu.java.scrapper.configuration.access;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.service.SendMessageService;
import ru.tinkoff.edu.java.scrapper.service.rabbit.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitQueueAccessConfig {
    @Bean
    public SendMessageService sendMessageService(ApplicationConfig config, RabbitTemplate rabbitTemplate) {
        return new ScrapperQueueProducer(config, rabbitTemplate);
    }
}
