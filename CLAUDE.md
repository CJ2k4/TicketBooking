# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Seat/ticket booking service ("ShowZzz"). Spring Boot 4.1.1, Java 21, PostgreSQL, Flyway, JPA.

**Current state: skeleton.** Only JPA entities and Flyway migrations exist — there are no controllers, services, or repositories yet. `DemoApplicationTests.contextLoads()` is the only test. Most work here is greenfield: build the web/service/repository layers on top of the schema described below.

## Commands

```bash
./mvnw spring-boot:run                 # run app (needs Postgres on :5432)
docker compose up -d db                # start Postgres (seatbooking/seat/seat)
./mvnw test                            # all tests
./mvnw test -Dtest=DemoApplicationTests#contextLoads   # single test / method
./mvnw package                         # build jar into target/
```

Run against a throwaway Testcontainers Postgres instead of docker-compose by launching
`com.TicketBooking.demo.TestDemoApplication` (test sources) — same app, container-backed datasource.

Datasource is overridable via `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

## Schema is Flyway-owned

`spring.jpa.hibernate.ddl-auto: validate` — Hibernate never creates or alters tables. Any entity field change
**must** be paired with a new `src/main/resources/db/migration/V<n>__<name>.sql`; otherwise the context fails to
start on validation. Never edit an already-applied migration.

## Domain model and invariants

The interesting logic lives in the schema constraints, not (yet) in Java:

- **`show_event`** ← `Show` entity. Table is deliberately *not* named `show` (reserved word in Postgres); the entity
  field `showId` maps to column `id`.
- **`seat`** — unique on `(show_id, row_label, seat_number)`.
- **`booking`** — the core of the design. A booking is a *hold* that later becomes a confirmation:
  `HELD → CONFIRMED | EXPIRED | CANCELLED` (`BookingStatus.isActive()` = HELD or CONFIRMED).
  - `ux_seat_active_booking`: partial unique index on `seat_id WHERE status IN ('HELD','CONFIRMED')` — **this is
    the double-booking guard**. Concurrent hold attempts on the same seat surface as a unique-constraint violation,
    which the service layer is expected to translate into a "seat unavailable" response. Don't replace it with
    application-level locking.
  - `ck_booking_confirmed_fields`: a CONFIRMED row must have both `confirmed_at` and `booking_reference`.
  - `@Version` on `Booking` → optimistic locking on updates.
  - `ix_booking_sweeper`: partial index on `hold_expires_at WHERE status = 'HELD'`, for the not-yet-written
    background job that expires stale holds.
- **`idempotency_key`** — keyed by `key_value`, scoped by `user_id` + `endpoint`, stores `request_hash` and the
  cached `response_status`/`response_body`. Intended for replay-safe POSTs (hold/confirm). The `IdempotencyKey`
  entity currently maps only a subset of these columns.

Commented-out config in `application.yml` sketches the intended knobs: `booking.hold-duration` (PT5M),
`max-seats-per-hold` (6), `sweeper-interval-ms`.

## Known rough edges

- `Seat` and `IdempotencyKey` have no getters/setters/constructors and are not fully mapped — flesh them out when
  the repository layer arrives.
- All timestamps are `Instant` / `timestamptz`, with `hibernate.jdbc.time_zone: UTC`. Keep it that way.
- `TestcontainersConfiguration` pins `postgres:latest` while docker-compose runs `postgres:16-alpine`.
- `spring.jpa.open-in-view` is `true` (Boot default); prefer explicit fetching over relying on it.
