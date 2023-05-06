package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
