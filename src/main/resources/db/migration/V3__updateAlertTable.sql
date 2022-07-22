ALTER TABLE alert
    DROP COLUMN status;

ALTER TABLE alert
    ADD status VARCHAR(255);