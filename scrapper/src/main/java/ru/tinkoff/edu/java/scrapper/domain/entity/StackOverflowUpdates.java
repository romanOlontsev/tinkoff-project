package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "stackoverflow_updates", schema = "link_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StackOverflowUpdates {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "is_answered")
    private boolean isAnswered;
    @Column(name = "answer_count")
    private Integer answerCount;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Link.class)
    @PrimaryKeyJoinColumn
    private Link link;
}