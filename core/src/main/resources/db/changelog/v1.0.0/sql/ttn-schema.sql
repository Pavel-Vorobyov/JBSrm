DROP TABLE IF EXISTS ttn;

DROP TYPE IF EXISTS ttn_state CASCADE;
CREATE TYPE ttn_state AS ENUM ('ACCEPTED', 'CHECK_COMPLETE', 'TRANSPORTATION_STARTED', 'DELIVERED');

create or replace function as_text(i ttn_state)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

CREATE TABLE IF NOT EXISTS ttn
(
	id serial PRIMARY KEY,
	create_at DATE NOT NULL,
	driver_id serial NOT NULL,
	transport_id serial NOT NULL,
	ttnstate ttn_state,
	created_by serial NOT NULL,
	deleted BOOLEAN
);

CREATE INDEX IF NOT EXISTS idx_fts_ttn ON ttn
  USING gin(as_tsvector(as_text(id), as_text(ttnstate)));