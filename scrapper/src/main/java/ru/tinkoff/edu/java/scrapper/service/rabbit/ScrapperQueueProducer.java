package ru.tinkoff.edu.java.scrapper.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.SendMessageService;

//@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer implements SendMessageService {
    private final ApplicationConfig config;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(config.queryName(), update);
    }
}
