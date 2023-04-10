--liquibase formatted sql

--changeset admin:3
INSERT INTO link_info.chat(chat_id) VALUES (333);
INSERT INTO link_info.chat(chat_id) VALUES (6633);
INSERT INTO link_info.link(url, chat_id) VALUES ('Gaga.url', 333);
INSERT INTO link_info.link(url, chat_id) VALUES ('first.url', 6633);
INSERT INTO link_info.link(url, chat_id) VALUES ('second.url', 6633);