package ru.tinkoff.edu.java.scrapper.model.dto.updates;

import lombok.*;
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
