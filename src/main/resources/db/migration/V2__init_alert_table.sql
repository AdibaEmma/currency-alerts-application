CREATE TABLE alert
(
    alert_id             BIGINT NOT NULL,
    currency_currency_id BIGINT,
    target_price         DECIMAL,
    status               INTEGER,
    created_at           TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_alert PRIMARY KEY (alert_id)
);

ALTER TABLE alert
    ADD CONSTRAINT FK_ALERT_ON_CURRENCY_CURRENCYID FOREIGN KEY (currency_currency_id) REFERENCES currency (currency_id);