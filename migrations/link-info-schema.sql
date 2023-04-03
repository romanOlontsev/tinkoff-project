--liquibase formatted sql

--changeset admin:1
CREATE SCHEMA IF NOT EXISTS link_info;
CREATE TABLE IF NOT EXISTS link_info.chat
(
    chat_id bigint NOT NULL PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS link_info.link
(
    id      bigint       NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    url     varchar(512) NOT NULL,
    chat_id bigint       NOT NULL REFERENCES link_info.chat ON DELETE CASCADE
);