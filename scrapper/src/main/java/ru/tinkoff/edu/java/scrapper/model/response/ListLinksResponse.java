package ru.tinkoff.edu.java.scrapper.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
