package ru.tinkoff.edu.java.scrapper.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
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
public class GitHubRepositoryInfoResponse {
    private Long id;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;
    @JsonProperty("forks_count")
    private Integer forksCount;
    @JsonProperty("watchers")
    private Integer watchers;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class Owner {
        @JsonProperty("login")
        private String ownerLogin;
        @JsonProperty("id")
        private String ownerId;
    }
}
