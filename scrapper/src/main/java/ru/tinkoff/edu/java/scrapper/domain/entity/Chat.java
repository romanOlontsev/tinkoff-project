package ru.tinkoff.edu.java.scrapper.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chat", schema = "link_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chat {
    @Id
    @Column(name = "chat_id")
    private Long tgChatId;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    private List<Link> links;

    public void addLink(Link link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }
}
