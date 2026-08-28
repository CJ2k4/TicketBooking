CREATE UNIQUE INDEX ux_seat_active_booking
ON booking(seat_id)
WHERE status IN ('HELD', 'CONFIRMED');