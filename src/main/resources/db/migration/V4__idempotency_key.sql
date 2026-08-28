CREATE TABLE idempotency_key (
                                 key_value        varchar(120) PRIMARY KEY,
                                 user_id          uuid         NOT NULL,
                                 endpoint         varchar(120) NOT NULL,
                                 request_hash     varchar(64)  NOT NULL,
                                 response_status  int,
                                 response_body    text,
                                 created_at       timestamptz  NOT NULL DEFAULT now(),
                                 completed_at     timestamptz
);

CREATE INDEX ix_idempotency_created ON idempotency_key (created_at);