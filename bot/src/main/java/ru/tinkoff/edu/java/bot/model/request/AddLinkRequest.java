package ru.tinkoff.edu.java.bot.model.request;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLinkRequest {
    private URI link;
}
