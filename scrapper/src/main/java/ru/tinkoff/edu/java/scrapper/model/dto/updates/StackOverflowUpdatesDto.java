package ru.tinkoff.edu.java.scrapper.model.dto.updates;

import lombok.*;
import ru.tinkoff.edu.java.scrapper.model.dto.UpdatesDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StackOverflowUpdatesDto  implements UpdatesDto {
    private Long id;
    private boolean isAnswered;
    private Integer answerCount;
}
