DROP TABLE IF EXISTS ttn_products;
CREATE TABLE IF NOT EXISTS ttn_products
(
	id serial PRIMARY KEY,
    ttn_id serial NOT NULL,
    product_id serial NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_ttn_products ON ttn_products
  USING gin(as_tsvector(id::TEXT, ttn_id::TEXT, product_id::TEXT));