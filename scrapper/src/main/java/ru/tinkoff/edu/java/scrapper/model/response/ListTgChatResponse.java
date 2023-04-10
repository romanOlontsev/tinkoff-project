package ru.tinkoff.edu.java.scrapper.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListTgChatResponse {
    private List<TgChatResponse> tgChatList;
    private Integer size;

    public void setTgChatList(List<TgChatResponse> tgChatList) {
        this.tgChatList = tgChatList;
        size = tgChatList.size();
    }
}
