package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

@Validated
public interface Links {
    @Operation(summary = "Получить все отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ссылка успешно убрана",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListLinksResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(value = "/links", produces = "application/json")
    ResponseEntity<ListLinksResponse> getLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ссылка успешно добавлена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/links",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<LinkResponse> postLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = true,
                    schema = @Schema())
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required = true,
                    schema = @Schema())
            @Valid
            @RequestBody AddLinkRequest body);

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ссылка успешно убрана",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ссылка не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))) })
    @DeleteMapping(value = "/links",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<LinkResponse> deleteLinks(
            @Parameter(
                    in = ParameterIn.HEADER,
                    required=true,
                    schema=@Schema())
            @RequestHeader(value="Tg-Chat-Id") Long tgChatId,
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required=true,
                    schema=@Schema())
            @Valid
            @RequestBody RemoveLinkRequest body);
}
