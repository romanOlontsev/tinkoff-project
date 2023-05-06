package ru.tinkoff.edu.java.bot.model.response;

import java.util.ArrayList;
import java.util.List;
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
