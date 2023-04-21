package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.OffsetDateTime;

@Entity
@DynamicInsert
@Table(name = "link", schema = "link_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;

    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    @Column(name = "last_check")
    private OffsetDateTime lastCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    @ToString.Exclude
    private Chat chat;
}
