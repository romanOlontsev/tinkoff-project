package ru.tinkoff.edu.java.scrapper.model.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkUpdateRequest {
    private Long tgChat;
    private String url;
    private String description;
    private Map<String, String> changes;
}
