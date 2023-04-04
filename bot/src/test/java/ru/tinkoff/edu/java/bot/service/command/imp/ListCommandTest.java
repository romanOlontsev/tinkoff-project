package ru.tinkoff.edu.java.bot.service.command.imp;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.service.LinkService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @InjectMocks
    private ListCommand listCommand;
    @Mock
    private LinkService linkService;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @Test
    void handle_shouldReturnStringFromService() {
        when(linkService.getLinkList()).thenReturn(List.of("some_string"));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        SendMessage handle = listCommand.handle(update);
        assertAll(() -> assertThat(handle.getParameters()).isNotNull()
                                                          .extracting("parse_mode")
                                                          .isEqualTo("HTML"),
                () -> assertThat(handle.getParameters()).isNotNull()
                                                        .extracting("text")
                                                        .isEqualTo("some_string")
        );
    }

    @Test
    void handle_shouldReturnStringByDefault() {
        String listCommandAnswer = ListCommand.getLINK_LIST_IS_EMPTY();

        when(linkService.getLinkList()).thenReturn(List.of());
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        SendMessage handle = listCommand.handle(update);
        assertAll(() -> assertThat(handle.getParameters()).isNotNull()
                                                          .extracting("parse_mode")
                                                          .isEqualTo("HTML"),
                () -> assertThat(handle.getParameters()).isNotNull()
                                                        .extracting("text")
                                                        .isEqualTo(listCommandAnswer)
        );
    }
}