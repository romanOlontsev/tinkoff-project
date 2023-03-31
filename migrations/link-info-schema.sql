create schema link_info;
--
--     create table link
--     (
--         id  int          not null unique primary key,
--         url varchar(512) not null
--     );

CREATE TABLE link_info.chat
(
    chat_id bigint NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY (chat_id)
);

CREATE TABLE link_info.link
(
    id      bigint       NOT NULL GENERATED ALWAYS AS IDENTITY (
        start 1
 ),
    url     varchar(512) NOT NULL,
    chat_id bigint       NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY (id),
    CONSTRAINT FK_1 FOREIGN KEY (chat_id) REFERENCES link_info.chat (chat_id)
);

-- CREATE INDEX FK_2 ON link_info.link
--     (
--      chat_id
--         );

