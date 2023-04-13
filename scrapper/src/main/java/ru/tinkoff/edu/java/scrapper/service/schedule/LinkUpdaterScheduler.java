package ru.tinkoff.edu.java.scrapper.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {

    private final LinkService linkService;

    @Override
    @Scheduled(fixedDelayString = "#{@schedulingIntervalMillis}")
    public void update() {
        ListLinksResponse allLinksOrderByLastUpdate = linkService.findAllLinksOrderByLastUpdate();
        log.info(allLinksOrderByLastUpdate.getLinks().toString());

        log.info("Update something");
    }
}
