CREATE TABLE booking (
                         id                 uuid         PRIMARY KEY,
                         hold_id            uuid         NOT NULL,
                         seat_id            uuid         NOT NULL REFERENCES seat(id)       ON DELETE CASCADE,
                         show_id            uuid         NOT NULL REFERENCES show_event(id) ON DELETE CASCADE,
                         user_id            uuid         NOT NULL,

                         status             varchar(16)  NOT NULL,
                         hold_expires_at    timestamptz  NOT NULL,
                         confirmed_at       timestamptz,
                         booking_reference  varchar(16),

                         version            bigint       NOT NULL DEFAULT 0,
                         created_at         timestamptz  NOT NULL DEFAULT now(),
                         updated_at         timestamptz  NOT NULL DEFAULT now(),

                         CONSTRAINT ck_booking_status CHECK (
                             status IN ('HELD', 'CONFIRMED', 'CANCELLED', 'EXPIRED')
                             ),
                         CONSTRAINT ck_booking_confirmed_fields CHECK (
                             status <> 'CONFIRMED'
                                 OR (confirmed_at IS NOT NULL AND booking_reference IS NOT NULL)
                             )
);

CREATE INDEX ix_booking_hold ON booking (hold_id);
CREATE INDEX ix_booking_seat ON booking (seat_id);

CREATE INDEX ix_booking_sweeper
    ON booking (hold_expires_at)
    WHERE status = 'HELD';