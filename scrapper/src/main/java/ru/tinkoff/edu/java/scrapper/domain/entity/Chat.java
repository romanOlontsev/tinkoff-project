package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "chat", schema = "link_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @Column(name = "chat_id")
    private Long tgChatId;

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
//    @JoinColumn(name = "chat_id")
//    private List<Link> links;
}
