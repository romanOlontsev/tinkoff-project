package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.entity.Link;

import java.util.Optional;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrlAndChatId(String url, Chat chatId);
}
