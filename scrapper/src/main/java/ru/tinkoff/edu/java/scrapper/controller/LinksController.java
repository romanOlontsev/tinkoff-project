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
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class LinksController implements Links {
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true, schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId) throws DataNotFoundException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            ListLinksResponse listLinksResponse = new ListLinksResponse();
            listLinksResponse.setLinks(new ArrayList<>());
            listLinksResponse.setSize(12);
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
            LinkResponse build = LinkResponse.builder()
                                             .id(1L)
                                             .url(URI.create("https://google.com"))
                                             .build();
            return new ResponseEntity<>(build, HttpStatus.OK);
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
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<LinkResponse>(objectMapper.readValue("{\n  \"id\" : 0,\n  \"url\" : \"http://example.com/aeiou\"\n}", LinkResponse.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }

        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
