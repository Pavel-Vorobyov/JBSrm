DROP TABLE IF EXISTS deed;
CREATE TABLE IF NOT EXISTS deed
(
	id SERIAL PRIMARY KEY,
	product_id serial,
	price SERIAL NOT NULL,
	create_at DATE NOT NULL,
	deleted BOOLEAN NOT NULL
);

create or replace function as_text(i integer)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

create or replace function as_text(i date)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

create or replace function as_text(i boolean)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

CREATE INDEX IF NOT EXISTS idx_fts_deed ON deed
  USING gin(as_tsvector(as_text(id), as_text(product_id), as_text(price), as_text(create_at), as_text(deleted)  ));