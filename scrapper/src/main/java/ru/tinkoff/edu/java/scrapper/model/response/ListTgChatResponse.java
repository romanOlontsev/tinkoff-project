package ru.tinkoff.edu.java.scrapper.model.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ListTgChatResponse {
    private List<TgChatResponse> tgChatList;
    private Integer size;

    public void setTgChatList(List<TgChatResponse> tgChatList) {
        this.tgChatList = tgChatList;
        size = tgChatList.size();
    }
}
