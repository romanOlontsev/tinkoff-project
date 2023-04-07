package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinksController implements Links {
    private final HttpServletRequest request;

    private final LinksService linksService;

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true, schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            ListLinksResponse listLinksResponse = new ListLinksResponse();
            listLinksResponse.setLinks(List.of(LinkResponse.builder()
                                                           .id(2L)
                                                           .url(URI.create("https://habr.com/ru/companies/productivity_inside/articles/505430/"))
                                                           .build()));
            return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<LinkResponse> postLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody
            AddLinkRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            LinkResponse response = linksService.addLink(tgChatId,body);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<LinkResponse> deleteLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody
            RemoveLinkRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            LinkResponse build = LinkResponse.builder()
                                             .id(1L)
                                             .url(body.getLink())
                                             .build();
            return new ResponseEntity<>(build, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
