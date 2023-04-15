--liquibase formatted sql

--changeset admin:17
CREATE SCHEMA IF NOT EXISTS link_info;
CREATE TABLE IF NOT EXISTS link_info.chat
(
    chat_id bigint NOT NULL PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS link_info.link
(
    id              bigint       NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    url             varchar(512) NOT NULL,
    last_update     timestamp    NOT NULL DEFAULT '1970-01-01 00:00:00',
    last_check      timestamp    NOT NULL DEFAULT '1970-01-01 00:00:00',
    chat_id         bigint       NOT NULL REFERENCES link_info.chat ON DELETE CASCADE
);