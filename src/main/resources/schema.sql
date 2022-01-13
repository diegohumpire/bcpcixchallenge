DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base VARCHAR(4) NOT NULL,
    code VARCHAR(4) NOT NULL UNIQUE,
    rate NUMERIC(25,15) NOT NULL
);

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
)