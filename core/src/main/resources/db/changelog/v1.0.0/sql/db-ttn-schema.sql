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

INSERT INTO ttn (create_at, driver_id, transport_id, ttnstate, created_by, deleted)
	SELECT
	    (
	        CURRENT_DATE::DATE
	    ) AS create_at,
	    (
	        CASE (RANDOM() * 2 )::INT
	            WHEN 0 THEN 2
	            WHEN 1 THEN 4
	            WHEN 2 THEN 6
	        END
	    ) AS driver_id,
	    (
	        CASE (RANDOM() * 2 )::INT
	            WHEN 0 THEN 2
	            WHEN 1 THEN 4
	            WHEN 2 THEN 6
	        END
	    ) AS transport_id,
		(
			CASE (RANDOM() * 3)::INT
				WHEN 0 THEN 'ACCEPTED'
				WHEN 1 THEN 'CHECK_COMPLETE'
				WHEN 2 THEN 'DELIVERED'
				WHEN 3 THEN 'TRANSPORTATION_STARTED'
			END
		)::ttn_state AS ttnstate,
		num AS created_by,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;