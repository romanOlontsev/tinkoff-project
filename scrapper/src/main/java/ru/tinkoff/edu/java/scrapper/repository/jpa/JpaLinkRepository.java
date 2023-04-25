package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.entity.Link;

import java.util.List;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByChat(Chat chat);

    @Query(value = "SELECT l1.* FROM link_info.link l1 " +
            "WHERE l1.id = (" +
            "SELECT l2.id FROM link_info.link l2 " +
            "WHERE l2.chat_id = l1.chat_id " +
            "ORDER BY l2.last_check " +
            "LIMIT 1);", nativeQuery = true)
    List<Link> findOneOldestLinkByLastCheckForEachUser();

}
