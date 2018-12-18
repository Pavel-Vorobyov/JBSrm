DROP TABLE IF EXISTS product;

DROP TYPE IF EXISTS product_state CASCADE;
CREATE TYPE product_state AS ENUM ('ACCEPTED', 'CHECK_COMPLETE', 'DELIVERED', 'LOST');

CREATE TABLE IF NOT EXISTS product
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
    product_state product_state,
	required_type transport_type,
	amount INTEGER NOT NULL,
	deleted BOOLEAN NOT NULL
);

--CREATE INDEX IF NOT EXISTS idx_fts_product ON product
--  USING gin(as_tsvector(id, title, required_type, product_state, amount, is_deleted));

INSERT INTO product (title, product_state, required_type, amount, deleted)
	SELECT
		'title' || num AS title,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'ACCEPTED'::product_state
				WHEN 1 THEN 'CHECK_COMPLETE'::product_state
				WHEN 2 THEN 'DELIVERED'::product_state
			END
		) AS product_state,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'::transport_type
				WHEN 1 THEN 'REFRIGERATOR'::transport_type
				WHEN 2 THEN 'TANK'::transport_type
			END
		) AS required_type,
		num AS amount,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;