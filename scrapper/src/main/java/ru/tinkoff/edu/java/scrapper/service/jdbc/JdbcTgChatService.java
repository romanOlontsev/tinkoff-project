package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.exception.DataAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final TgChatRepository tgChatRepository;

    @Override
    public TgChatResponse registerChat(Long tgChatId) {
        TgChatResponse response = tgChatRepository.add(tgChatId);
        if (response.getTgChatId() < 0) {
            throw new DataAlreadyExistException("Чат с id=" + tgChatId + " уже существует");
        }
        return response;
    }
    @Override
    public TgChatResponse removeChat(Long tgChatId) {
        TgChatResponse response = tgChatRepository.remove(tgChatId);
        if (response.getTgChatId() < 0) {
            throw new DataNotFoundException("Чат с id=" + tgChatId + " не найден");
        }
        return response;
    }
}
