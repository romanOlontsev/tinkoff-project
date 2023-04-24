package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RestController
@RequiredArgsConstructor
public class TgChatController implements TgChat {
    private final HttpServletRequest request;
    private final TgChatService tgChatService;

    @Override
    public ResponseEntity<Void> registerChat(
            @Parameter(
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema())
            @PathVariable Long id) {
        tgChatService.registerChat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteChat(
            @Parameter(
                    in = ParameterIn.PATH,
                    required = true,
                    schema = @Schema())
            @PathVariable Long id) {
        tgChatService.removeChat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
