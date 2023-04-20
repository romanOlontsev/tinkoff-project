package ru.tinkoff.edu.java.scrapper.mapper;

import org.mapstruct.Mapper;
import ru.tinkoff.edu.java.scrapper.domain.entity.Chat;
import ru.tinkoff.edu.java.scrapper.model.response.TgChatResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    TgChatResponse chatToTgChatResponse(Chat chat);

    List<TgChatResponse> chatListToTgChatResponseList(List<Chat> chatList);
}
