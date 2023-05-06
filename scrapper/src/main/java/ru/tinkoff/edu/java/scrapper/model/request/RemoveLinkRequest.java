package ru.tinkoff.edu.java.scrapper.model.request;

import java.net.URI;
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
public class RemoveLinkRequest {
    private URI link;
}
