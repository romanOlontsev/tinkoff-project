package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class StackOverflowQuestionInfoDto {
    private List<Items> items;
    @JsonProperty("has_more")
    private Boolean hasMore;


    @Data
    @NoArgsConstructor
    private static class Items {
        //        @JsonProperty("")
        private List<String> tags;
        private Owner owner;
        @JsonProperty("creation_date")
        private OffsetDateTime creationDate;
        @JsonProperty("question_id")
        private Long questionId;
        private String link;
        private String title;
    }

    @Data
    @NoArgsConstructor
    private static class Owner {
        @JsonProperty("account_id")
        private Long accountId;
        @JsonProperty("user_id")
        private Long userId;
        private String link;
    }
}
