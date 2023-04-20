package ru.tinkoff.edu.java.scrapper.model.dto;

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
    private String type;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheck;
}
