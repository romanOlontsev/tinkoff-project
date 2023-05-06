package ru.tinkoff.edu.java.scrapper.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
