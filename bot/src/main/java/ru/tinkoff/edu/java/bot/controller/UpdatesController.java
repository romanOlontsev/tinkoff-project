package ru.tinkoff.edu.java.bot.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.model.request.LinkUpdateRequest;

@RestController
public class UpdatesController implements Updates{

    @Override
    public ResponseEntity<Void> postUpdates(
            @Parameter(
                    in = ParameterIn.DEFAULT,
                    required=true,
                    schema=@Schema())
            @Valid
            @RequestBody LinkUpdateRequest body) {
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}
