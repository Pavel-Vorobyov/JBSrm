DROP TABLE IF EXISTS ttn;

CREATE TABLE IF NOT EXISTS ttn
(
	id serial PRIMARY KEY,
	waybill_id serial,
	is_approved BOOLEAN
);