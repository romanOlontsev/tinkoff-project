package ru.tinkoff.edu.java.scrapper.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.response.ApiErrorResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handle(DataNotFoundException e) {
        ApiErrorResponse exceptionResponse = getApiErrorResponse(e, "404", "Ссылка не найдена");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            BadRequestException.class,
            DataAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handle(RuntimeException e) {
        ApiErrorResponse exceptionResponse = getApiErrorResponse(e, "400", "Некорректные параметры запроса");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private ApiErrorResponse getApiErrorResponse(Exception e, String code, String description) {
        ApiErrorResponse exceptionResponse = ApiErrorResponse.builder()
                                                             .code(code)
                                                             .description(description)
                                                             .exceptionName(e.getClass()
                                                                             .getName())
                                                             .exceptionMessage(e.getMessage())
                                                             .build();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            exceptionResponse.addStacktraceItem(stackTraceElement.toString());
        }
        return exceptionResponse;
    }


}
