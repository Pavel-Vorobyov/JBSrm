DROP TABLE IF EXISTS transport;

DROP TYPE IF EXISTS transport_type CASCADE;
CREATE TYPE transport_type AS ENUM ('COVERED_BODY', 'REFRIGERATOR', 'TANK');

create or replace function as_text(i transport_type)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

CREATE TABLE IF NOT EXISTS transport
(
	id serial PRIMARY KEY,
	title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	bodytype transport_type,
	consumption INTEGER NOT NULL,
	company_id serial,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_transport ON transport
  USING gin(as_tsvector(id::TEXT, title, as_text(bodytype), consumption::TEXT, company_id::TEXT));