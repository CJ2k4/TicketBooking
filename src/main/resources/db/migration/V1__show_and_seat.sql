CREATE TABLE show_event(
    id uuid primary key ,
    name varchar(200) NOT NULL,
    start_at timestamptz NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE seat(
    id uuid PRIMARY KEY,
    show_id uuid NOT NULL REFERENCES show_event(id) ON DELETE CASCADE ,
    row_label varchar(4) NOT NULL ,
    seat_number int NOT NULL,

    CONSTRAINT uq_seat_position UNIQUE (show_id, row_label, seat_number),
    CONSTRAINT ck_seat_number CHECK ( seat_number > 0)
);

CREATE INDEX ix_seat_show ON seat(show_id);