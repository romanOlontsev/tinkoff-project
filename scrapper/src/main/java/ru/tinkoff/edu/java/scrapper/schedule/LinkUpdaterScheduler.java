package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@Slf4j
@Component
@EnableConfigurationProperties(ApplicationConfig.class)
public class LinkUpdaterScheduler {

    private final ApplicationConfig config;

    public LinkUpdaterScheduler(ApplicationConfig config) {
        this.config = config;
    }

    @Scheduled(fixedDelayString = "#{@config.scheduler().interval()}")
    public void update() {
        log.info("Update something");
    }
}
