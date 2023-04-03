package ru.tinkoff.edu.java.scrapper.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkResponse {
    private Long id;

    private String url;
}
