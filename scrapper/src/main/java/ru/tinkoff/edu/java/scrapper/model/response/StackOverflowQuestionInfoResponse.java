package ru.tinkoff.edu.java.scrapper.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StackOverflowQuestionInfoResponse {
    private List<Items> items;
    @JsonProperty("has_more")
    private Boolean hasMore;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Items {
        private List<String> tags;
        private Owner owner;
        @JsonProperty("creation_date")
        private OffsetDateTime creationDate;
        @JsonProperty("last_edit_date")
        private OffsetDateTime lastEditDate;
        @JsonProperty("question_id")
        private Long questionId;
        private String link;
        private String title;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Owner {
        @JsonProperty("account_id")
        private Long accountId;
        @JsonProperty("user_id")
        private Long userId;
        private String link;
    }
}
