package ru.tinkoff.edu.java.scrapper.model.dto.updates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.tinkoff.edu.java.scrapper.model.dto.UpdatesDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GitHubUpdatesDto implements UpdatesDto {
    private Long id;
    private Integer forksCount;
    private Integer watchers;
}
