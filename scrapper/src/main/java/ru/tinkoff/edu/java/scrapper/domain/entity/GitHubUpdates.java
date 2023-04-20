package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "github_updates", schema = "link_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUpdates {
    @Id
    @Column(name = "id")
    private Long Id;
    @Column(name = "forks_count")
    private Integer forksCount;
    @Column(name = "watchers")
    private Integer watchers;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Link.class)
    @PrimaryKeyJoinColumn
    private Link link;
}
