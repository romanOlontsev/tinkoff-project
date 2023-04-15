package ru.tinkoff.edu.java.bot.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LinkUpdateRequest {
    private Long tgChat;
    private String url;
    private String description;
}
