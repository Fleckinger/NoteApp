CREATE DATABASE note;
USE note;

CREATE TABLE IF NOT EXISTS user
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    email           VARCHAR(255),
    password        VARCHAR(255),
    role            VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS note
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    status          VARCHAR(255),
    title           VARCHAR(255),
    content         MEDIUMTEXT,
    upload_date     datetime(6),
    user_id         BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);



