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
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    , generator = "link_seq")
//    @SequenceGenerator(name = "link_seq", sequenceName = "link_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    @Column(name = "last_check")
    private OffsetDateTime lastCheck;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chatId;
}
