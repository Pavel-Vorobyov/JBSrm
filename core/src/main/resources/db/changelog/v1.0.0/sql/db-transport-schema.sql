DROP TABLE IF EXISTS transport;

DROP TYPE IF EXISTS transport_type CASCADE;
CREATE TYPE transport_type AS ENUM ('COVERED_BODY', 'REFRIGERATOR', 'TANK');

CREATE TABLE IF NOT EXISTS transport
(
	id serial PRIMARY KEY,
	body_type transport_type,
	consumption INTEGER NOT NULL,
	company_id serial,
	deleted BOOLEAN NOT NULL
);

--CREATE INDEX IF NOT EXISTS idx_fts_transport ON transport
--  USING gin(as_tsvector(id, body_type::TEXT, consumption, client_id));

INSERT INTO transport (body_type, consumption, company_id, deleted)
	SELECT
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'::transport_type
				WHEN 1 THEN 'REFRIGERATOR'::transport_type
				WHEN 2 THEN 'TANK'::transport_type
			END
		) AS body_type,
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
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;