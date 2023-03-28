package ru.tinkoff.edu.java.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.response.LinkResponse;
import ru.tinkoff.edu.java.bot.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.client.ScrapperClient;

@RestController
@RequestMapping("/scrapper")
@RequiredArgsConstructor
public class ScrapperTestController {

    private final ScrapperClient scrapperClient;

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader String thChatId) {
        return scrapperClient.getLinks(thChatId)
                             .block();
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> postLinks(@RequestHeader String thChatId, @RequestBody AddLinkRequest addLinkRequest) {
        return scrapperClient.postLinks(thChatId, addLinkRequest)
                             .block();
    }

}
