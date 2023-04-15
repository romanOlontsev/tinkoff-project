--liquibase formatted sql

--changeset admin:17
INSERT INTO link_info.chat(chat_id) VALUES (333);
INSERT INTO link_info.chat(chat_id) VALUES (6633);
INSERT INTO link_info.chat(chat_id) VALUES (99999);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://github.com/Gadetych/my-first-blog', 333);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://stackoverflow.com/questions/54189839/how-to-store-offsetdatetime-to-postgresql-timestamp-with-time-zone-column', 333);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://github.com/Gadetych/tinkoff-project', 6633);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://stackoverflow.com/questions/11843658/update-a-row-using-spring-jdbctemplate', 6633);