/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.link_info.tables.pojos;


import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Generated;
import java.beans.ConstructorProperties;
import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;

    public Chat() {}

    public Chat(Chat value) {
        this.chatId = value.chatId;
    }

    @ConstructorProperties({ "chatId" })
    public Chat(
        @NotNull Long chatId
    ) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>LINK_INFO.CHAT.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>LINK_INFO.CHAT.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Chat other = (Chat) obj;
        if (this.chatId == null) {
            if (other.chatId != null)
                return false;
        }
        else if (!this.chatId.equals(other.chatId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chat (");

        sb.append(chatId);

        sb.append(")");
        return sb.toString();
    }
}