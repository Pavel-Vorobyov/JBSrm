DROP TABLE IF EXISTS ttn;

DROP TYPE IF EXISTS ttn_state CASCADE;
CREATE TYPE ttn_state AS ENUM ('ISSUED', 'CHECK_COMPLETE', 'DELIVERED');

CREATE TABLE IF NOT EXISTS ttn
(
	id serial PRIMARY KEY,
	ttn_state ttn_state,
	deleted BOOLEAN
);

INSERT INTO ttn (ttn_state, deleted)
	SELECT
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'ISSUED'::ttn_state
				WHEN 1 THEN 'CHECK_COMPLETE'::ttn_state
				WHEN 2 THEN 'DELIVERED'::ttn_state
			END
		) AS ttn_state,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;