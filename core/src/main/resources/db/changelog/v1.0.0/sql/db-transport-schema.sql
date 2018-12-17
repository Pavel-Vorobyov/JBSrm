DROP TABLE IF EXISTS transport;

DROP TYPE IF EXISTS transport_type CASCADE;
CREATE TYPE transport_type AS ENUM ('COVERED_BODY', 'REFRIGERATOR', 'TANK');

CREATE TABLE IF NOT EXISTS transport
(
	id serial PRIMARY KEY,
	body_type transport_type,
	consumption INTEGER NOT NULL,
	client_id serial,
	deleted BOOLEAN NOT NULL
);

--CREATE INDEX IF NOT EXISTS idx_fts_transport ON transport
--  USING gin(as_tsvector(id, body_type::TEXT, consumption, client_id));