package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

@RestController
public class LinksController implements Links {

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true, schema = @Schema())
            @RequestHeader(
                    value = "Tg-Chat-Id")
            Long tgChatId) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<ListLinksResponse>(objectMapper.readValue("{\n  \"size\" : 6,\n  \"links\" : [ {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  }, {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  } ]\n}", ListLinksResponse.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<ListLinksResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }

        return new ResponseEntity<ListLinksResponse>(HttpStatus.NOT_IMPLEMENTED);
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
