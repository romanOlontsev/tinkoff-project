package ru.tinkoff.edu.java.scrapper.dto.updates;

import lombok.*;
import ru.tinkoff.edu.java.scrapper.dto.UpdatesDto;

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
