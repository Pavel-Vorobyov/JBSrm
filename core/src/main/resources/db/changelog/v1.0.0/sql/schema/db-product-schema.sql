DROP TYPE IF EXISTS product_state CASCADE;
CREATE TYPE product_state AS ENUM ('ACCEPTED', 'CHECK_COMPLETE', 'DELIVERED', 'LOST');

DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product
(
	id serial PRIMARY KEY,
    product_details_id serial NOT NULL,
	amount INTEGER ,
	productstate product_state,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_product ON product
  USING gin(as_tsvector(id::TEXT));