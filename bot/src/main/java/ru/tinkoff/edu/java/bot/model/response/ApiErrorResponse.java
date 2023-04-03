package ru.tinkoff.edu.java.bot.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ApiErrorResponse {
    private String description;

    private String code;

    private String exceptionName;

    private String exceptionMessage;

    private List<String> stacktrace;

    public ApiErrorResponse addStacktraceItem(String stacktraceItem) {
        if (this.stacktrace == null) {
            this.stacktrace = new ArrayList<>();
        }
        this.stacktrace.add(stacktraceItem);
        return this;
    }

}
