package ru.tinkoff.edu.java.scrapper.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitHubRepositoryInfoDto {
    private Long id;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("owner")
    private Owner owner;

    @Data
    @NoArgsConstructor
    private static class Owner {
        @JsonProperty("login")
        private String ownerLogin;
        @JsonProperty("id")
        private String ownerId;
    }
}
