package ru.tinkoff.edu.java.scrapper.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
    private String description;
    private String code;
    @JsonProperty(value = "exception_name")
    private String exceptionName;
    @JsonProperty(value = "exception_message")
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
