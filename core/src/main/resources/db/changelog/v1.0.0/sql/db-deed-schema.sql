DROP TABLE IF EXISTS deed;
CREATE TABLE IF NOT EXISTS deed
(
	id SERIAL PRIMARY KEY,
	price SERIAL NOT NULL,
	create_at DATE NOT NULL,
	deleted BOOLEAN NOT NULL
);

INSERT INTO deed (price, create_at, deleted)
	SELECT
		num AS price,
		(
			CURRENT_DATE::DATE
		) AS create_at,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;