package ru.tinkoff.edu.java.scrapper.model.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
