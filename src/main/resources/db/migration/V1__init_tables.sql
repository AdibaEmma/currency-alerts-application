CREATE TABLE app_role
(
    role_id   BIGINT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255)          NULL,
    CONSTRAINT pk_app_role PRIMARY KEY (role_id)
);

CREATE TABLE app_user
(
    user_id    BIGINT AUTO_INCREMENT NOT NULL,
    username   VARCHAR(255)          NULL,
    password   VARCHAR(255)          NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (user_id)
);

CREATE TABLE currency
(
    currency_id   BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    symbol        VARCHAR(255)          NULL,
    current_price DECIMAL               NULL,
    enabled       BIT(1)                NOT NULL,
    created_time  datetime              NULL,
    uuid          VARCHAR(255)          NULL,
    CONSTRAINT pk_currency PRIMARY KEY (currency_id)
);

CREATE TABLE currency_alert
(
    alert_id             BIGINT       NOT NULL,
    currency_currency_id BIGINT       NULL,
    target_price         DECIMAL      NULL,
    status               VARCHAR(255) NULL,
    created_at           datetime     NULL,
    user_id              BIGINT       NULL,
    CONSTRAINT pk_currency_alert PRIMARY KEY (alert_id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_username UNIQUE (username);

ALTER TABLE currency_alert
    ADD CONSTRAINT FK_CURRENCY_ALERT_ON_CURRENCY_CURRENCY FOREIGN KEY (currency_currency_id) REFERENCES currency (currency_id);

ALTER TABLE currency_alert
    ADD CONSTRAINT FK_CURRENCY_ALERT_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (user_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES app_role (role_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES app_user (user_id);