package ru.tinkoff.edu.java.scrapper.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.parser.result.StackOverflowResultRecord;
import ru.tinkoff.edu.java.scrapper.model.response.ListTgChatResponse;
import ru.tinkoff.edu.java.scrapper.model.response.StackOverflowQuestionInfoResponse;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.client.StackOverflowClient;

@RestController
@AllArgsConstructor
public class StackOverflowTestController {
    private final StackOverflowClient stackOverflowClient;
    private final TgChatService jpaTgChatService;

    @GetMapping("/stackoverflow/{id}")
    public StackOverflowQuestionInfoResponse getQuesInfo(@PathVariable Long id) {
        return stackOverflowClient.getStackOverflowQuestionInfo(new StackOverflowResultRecord(String.valueOf(id)))
                                  .block();
    }

    @GetMapping("/stackoverflow")
    public StackOverflowQuestionInfoResponse getQuesInfoWithNull() {
        return stackOverflowClient.getStackOverflowQuestionInfo(null)
                                  .block();
    }

    @GetMapping("/some/{id}")
    public TgChatResponse getSome(@PathVariable Long id) {
        return jpaTgChatService.registerChat(id);
    }

    @GetMapping("/some2/{id}")
    public TgChatResponse getSome2(@PathVariable Long id) {
        return jpaTgChatService.removeChat(id);
    }

    @GetMapping("/some")
    public ListTgChatResponse getSome3() {
        return jpaTgChatService.findAll();
    }

}
