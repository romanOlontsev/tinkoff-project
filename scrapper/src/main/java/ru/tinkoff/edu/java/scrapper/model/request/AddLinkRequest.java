package ru.tinkoff.edu.java.scrapper.model.request;

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
