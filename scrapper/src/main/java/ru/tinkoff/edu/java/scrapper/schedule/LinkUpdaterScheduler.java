package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        log.info("Update something");
    }
}
