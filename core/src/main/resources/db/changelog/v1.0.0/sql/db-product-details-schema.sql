DROP TABLE IF EXISTS product_details;

DROP TYPE IF EXISTS required_type CASCADE;
CREATE TYPE required_type AS ENUM ('COVERED_BODY', 'REFRIGERATOR', 'TANK');

create or replace function as_text(i required_type)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

CREATE TABLE IF NOT EXISTS product_details
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
    description VARCHAR (1000) COLLATE pg_catalog."default" NOT NULL,
    price serial NOT NULL,
	required_type required_type,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_product_details ON product_details
  USING gin(as_tsvector(id::TEXT, title::TEXT, deleted::TEXT));

INSERT INTO product_details (title, description, price, required_type, deleted)
	SELECT
		'title' || num AS title,
		'Description....' AS description,
		(
		    CASE (RANDOM() * 4)::INT
		        WHEN 0 THEN 50
		        WHEN 1 THEN 100
		        WHEN 2 THEN 150
		        WHEN 3 THEN 200
		        WHEN 4 THEN 250
		    END
		) AS price,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'
				WHEN 1 THEN 'REFRIGERATOR'
				WHEN 2 THEN 'TANK'
			END
		)::required_type AS required_type,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;