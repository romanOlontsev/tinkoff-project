package ru.tinkoff.edu.java.scrapper.model.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ListLinksResponse {
    private List<LinkResponse> links;
    private Integer size;

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
        size = links.size();
    }
}
