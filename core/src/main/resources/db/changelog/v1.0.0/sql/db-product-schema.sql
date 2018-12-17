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