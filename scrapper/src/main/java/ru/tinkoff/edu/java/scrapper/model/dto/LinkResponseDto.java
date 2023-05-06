package ru.tinkoff.edu.java.scrapper.model.dto;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LinkResponseDto {
    private Long id;
    private URI url;
    private String type;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheck;
}
