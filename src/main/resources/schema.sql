DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base VARCHAR(4) NOT NULL,
    code VARCHAR(4) NOT NULL UNIQUE,
    rate NUMERIC(25,15) NOT NULL
)