DROP TABLE IF EXISTS ttn;

DROP TYPE IF EXISTS ttn_state CASCADE;
CREATE TYPE ttn_state AS ENUM ('ISSUED', 'CHECK_COMPLETE', 'DELIVERED');

CREATE TABLE IF NOT EXISTS ttn
(
	id serial PRIMARY KEY,
	waybill_id serial,
	ttn_state ttn_state,
	is_approved BOOLEAN
);