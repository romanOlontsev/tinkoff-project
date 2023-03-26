package ru.tinkoff.edu.java.scrapper.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ListLinksResponse {

    private List<LinkResponse> links;

    private Integer size;

    public ListLinksResponse addLinksItem(LinkResponse linksItem) {
        if (this.links == null) {
            this.links = new ArrayList<LinkResponse>();
        }
        this.links.add(linksItem);
        return this;
    }
}
