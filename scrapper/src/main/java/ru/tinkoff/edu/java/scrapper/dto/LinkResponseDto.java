package ru.tinkoff.edu.java.scrapper.dto;

import lombok.*;

import java.net.URI;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LinkResponseDto {
    private Long id;
    private URI url;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheck;
}
