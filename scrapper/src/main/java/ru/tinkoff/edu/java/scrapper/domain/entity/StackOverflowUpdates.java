package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stackoverflow_updates", schema = "link_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StackOverflowUpdates {
    @Id
    @Column(name = "id")
    private Long Id;
    @Column(name = "is_answered")
    private boolean isAnswered;
    @Column(name = "answer_count")
    private Integer answerCount;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Link.class)
    @PrimaryKeyJoinColumn
    private Link link;
}