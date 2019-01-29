DROP TABLE IF EXISTS company;
CREATE TABLE IF NOT EXISTS company
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
    email VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	phone VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	deleted BOOLEAN NOT NULL
);

create or replace function as_tsvector(column_names variadic text[])
	returns tsvector as $$
	declare
		result_vector tsvector := to_tsvector('');
		cn text;
	begin
		foreach cn in array column_names loop
			result_vector := result_vector || to_tsvector('simple', coalesce(cn, ''));
		end loop;

			return result_vector;
	end
	$$ language 'plpgsql' immutable;

CREATE INDEX IF NOT EXISTS idx_fts_company ON company
  USING gin(as_tsvector(title, email, phone));