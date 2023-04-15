--liquibase formatted sql

--changeset admin:1
INSERT INTO link_info.chat(chat_id) VALUES (333);
INSERT INTO link_info.link(url, chat_id) VALUES ('Gaga.url', 333);