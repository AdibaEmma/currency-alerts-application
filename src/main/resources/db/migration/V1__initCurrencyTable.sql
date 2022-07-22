CREATE TABLE currency
(
    currency_id   BIGINT  NOT NULL,
    name          VARCHAR(255),
    symbol        VARCHAR(255),
    current_price DECIMAL,
    enabled       BOOLEAN NOT NULL,
    created_time  TIMESTAMP WITHOUT TIME ZONE,
    uuid          VARCHAR(255),
    CONSTRAINT pk_currency PRIMARY KEY (currency_id)
);

ALTER TABLE currency
    ADD CONSTRAINT uc_currency_symbol UNIQUE (symbol);