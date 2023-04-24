package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;

import java.util.Optional;

public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
}
