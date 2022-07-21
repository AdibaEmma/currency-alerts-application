CREATE TABLE currency
(
    currency_id   BIGINT NOT NULL,
    name          VARCHAR(255) NULL,
    symbol        VARCHAR(255) NULL,
    current_price DECIMAL NULL,
    enabled       BIT(1) NOT NULL,
    created_time  datetime NULL,
    uuid          VARCHAR(255) NULL,
    CONSTRAINT pk_currency PRIMARY KEY (currency_id)
);