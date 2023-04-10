package ru.tinkoff.edu.java.scrapper.service.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

@Slf4j
@Component
public class LinkUpdaterScheduler implements LinkUpdater {

    @Override
    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        log.info("Update something");
    }
}
