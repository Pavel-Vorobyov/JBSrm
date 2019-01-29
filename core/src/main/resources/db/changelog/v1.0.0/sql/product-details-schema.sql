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
	requiredtype required_type,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_product_details ON product_details
  USING gin(as_tsvector(id::TEXT, title::TEXT, deleted::TEXT));