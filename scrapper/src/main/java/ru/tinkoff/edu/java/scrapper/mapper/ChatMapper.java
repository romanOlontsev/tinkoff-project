package ru.tinkoff.edu.java.scrapper.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    TgChatResponse chatToTgChatResponse(Chat chat);

    List<TgChatResponse> chatListToTgChatResponseList(List<Chat> chatList);
}
