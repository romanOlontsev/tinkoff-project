package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tinkoff.edu.java.scrapper.model.response.ApiErrorResponse;

@Validated
public interface TgChat {
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Чат зарегистрирован"
                    //                    , content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = LinkResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/tg-chat/{id}",
            produces = {"application/json"})
    ResponseEntity<Void> registerChat(
            @Parameter(
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema())
            @PathVariable("id") Long id);

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Чат успешно удалён"
//                    , content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = LinkResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Чат не существует",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/tg-chat/{id}",
            produces = {"application/json"})
    ResponseEntity<Void> deleteChat(
            @Parameter(
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema())
            @PathVariable("id") Long id);
}
