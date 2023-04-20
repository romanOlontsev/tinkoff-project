--liquibase formatted sql

--changeset admin:21
INSERT INTO link_info.chat(chat_id) VALUES (333);
INSERT INTO link_info.chat(chat_id) VALUES (6633);
INSERT INTO link_info.chat(chat_id) VALUES (99999);
INSERT INTO link_info.link(url, type, chat_id) VALUES ('https://github.com/Gadetych/my-first-blog', 'github', 333);
INSERT INTO link_info.link(url, type, chat_id) VALUES ('https://stackoverflow.com/questions/54189839/how-to-store-offsetdatetime-to-postgresql-timestamp-with-time-zone-column','stack', 333);
INSERT INTO link_info.link(url, type, chat_id) VALUES ('https://github.com/Gadetych/tinkoff-project','github', 6633);
INSERT INTO link_info.link(url, type, chat_id) VALUES ('https://stackoverflow.com/questions/11843658/update-a-row-using-spring-jdbctemplate','stack', 6633);
INSERT INTO link_info.github_updates(id) VALUES (1);
INSERT INTO link_info.github_updates(id) VALUES (3);
INSERT INTO link_info.stackoverflow_updates(id) VALUES (2);
INSERT INTO link_info.stackoverflow_updates(id) VALUES (4);