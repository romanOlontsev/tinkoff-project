package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.model.response.ListLinksResponse;

import static org.jooq.impl.DSL.select;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.link_info.tables.Chat.CHAT;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.link_info.tables.Link.LINK;

@Service
@RequiredArgsConstructor
public class JooqLinkService {
    // String query = "INSERT INTO link_info.link(url, chat_id) " +
//                "SELECT ?,? " +
//                "WHERE NOT EXISTS(" +
//                "SELECT url FROM link_info.link WHERE chat_id=? AND url=?)";
    private final DSLContext context;

    @Transactional
    public LinkResponse addLink(Long tgChatId, AddLinkRequest request) {
        boolean isExists = context.fetchExists(select().from(CHAT)
                                                       .where(CHAT.CHAT_ID.eq(tgChatId)));
        if (!isExists) {
            throw new BadRequestException("Чат с id=" + tgChatId + " не существует");
        }
        String host = request.getLink()
                             .getHost();
        Record1<Long> returningResult = context.insertInto(LINK, LINK.TYPE, LINK.URL, LINK.CHAT_ID)
                                               .values(host, request.getLink()
                                                                                 .toString(), tgChatId)
                                               .returningResult(LINK.ID)
                                               .fetchOne();
        return LinkResponse.builder()
                           .url(request.getLink())
                           .id(returningResult.value1())
                           .build();
    }

    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest request) {
        return null;

    }

    public ListLinksResponse findAllLinksByTgChatId(Long tgChatId) {
        return null;
    }
}
