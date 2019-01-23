DROP TABLE IF EXISTS transport;

DROP TYPE IF EXISTS transport_type CASCADE;
CREATE TYPE transport_type AS ENUM ('COVERED_BODY', 'REFRIGERATOR', 'TANK');

DROP TYPE IF EXISTS transport_state CASCADE;
CREATE TYPE transport_state AS ENUM ('FREE', 'BUSY');

create or replace function as_text(i transport_type)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

create or replace function as_text(i transport_state)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

CREATE TABLE IF NOT EXISTS transport
(
	id serial PRIMARY KEY,
	title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	body_type transport_type,
	consumption INTEGER NOT NULL,
	company_id serial,
	transport_state transport_state,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_transport ON transport
  USING gin(as_tsvector(id::TEXT, title, as_text(body_type), consumption::TEXT, company_id::TEXT, as_text(transport_state)));

INSERT INTO transport (title, body_type, consumption, company_id, transport_state, deleted)
	SELECT
	    'title' || num AS title,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'
				WHEN 1 THEN 'REFRIGERATOR'
				WHEN 2 THEN 'TANK'
			END
		)::transport_type AS body_type,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 50
				WHEN 1 THEN 100
				WHEN 2 THEN 150
			END
		) AS consumption,
		num AS company_id,
																										 (
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'FREE'
				WHEN 1 THEN 'FREE'
				WHEN 2 THEN 'BUSY'
			END
		)::transport_state AS transport_state,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted

	FROM GENERATE_SERIES(1, 300) num;