package ru.tinkoff.edu.java.scrapper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.edu.java.scrapper.domain.entity.Link;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;

import java.net.URI;

@Mapper(componentModel = "spring", imports = {URI.class})
public interface LinkMapper {
    @Mapping(target = "url", expression = "java(addLinkRequest.getLink().toString())")
    Link addLinkRequestToLink(AddLinkRequest addLinkRequest);

    @Mapping(target = "url", expression = "java(URI.create(link.getUrl()))")
    LinkResponse linkToLinkResponse(Link link);
}
