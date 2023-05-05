--liquibase formatted sql

--changeset admin:23
CREATE SCHEMA IF NOT EXISTS link_info;
CREATE TABLE IF NOT EXISTS link_info.chat
(
    chat_id bigint NOT NULL PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS link_info.link
(
    id              bigint       NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    url             varchar(512) NOT NULL,
    type            varchar(24)  NOT NULL CHECK (type='stackoverflow' OR type='github'),
    last_update     timestamp    NOT NULL DEFAULT '1970-01-01 00:00:00',
    last_check      timestamp    NOT NULL DEFAULT '1970-01-01 00:00:00',
    chat_id         bigint       NOT NULL REFERENCES link_info.chat ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS link_info.github_updates
(
    id              bigint    NOT NULL PRIMARY KEY REFERENCES link_info.link ON DELETE CASCADE,
    forks_count     int       NOT NULL DEFAULT 0,
    watchers        int       NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS link_info.stackoverflow_updates
(
    id              bigint    NOT NULL PRIMARY KEY REFERENCES link_info.link ON DELETE CASCADE,
    is_answered     boolean   NOT NULL DEFAULT false,
    answer_count    int       NOT NULL DEFAULT 0
);