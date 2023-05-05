package ru.tinkoff.edu.java.bot.service.update;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UpdateService;

@Slf4j
@Service
public class ScrapperUpdates implements UpdateService {
    public void receiver(LinkUpdateRequest update) {
        log.info("Have a message: " + update.toString());
    }

}
