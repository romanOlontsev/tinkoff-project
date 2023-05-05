package ru.tinkoff.edu.java.bot.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UpdateService;

@Service
@Slf4j
@RabbitListener(queues = {"${app.query-name}"})
public class ScrapperQueueListener implements UpdateService {
    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        log.info("Have a message: " + update.toString());
//        throw new RuntimeException();
    }

}
