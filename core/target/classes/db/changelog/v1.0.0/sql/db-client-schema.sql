DROP TABLE IF EXISTS client;

DROP TYPE IF EXISTS client_role CASCADE;
CREATE TYPE client_role AS ENUM ('SYSTEM_ADMIN', 'ADMIN', 'DISPATCHER', 'MANAGER', 'DRIVER', 'COMPANY_OWNER');

CREATE TABLE IF NOT EXISTS client
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
    email VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	phone VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	client_role client_role NOT NULL,
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

CREATE INDEX IF NOT EXISTS idx_fts_client ON client
  USING gin(as_tsvector(title, email, phone));

INSERT INTO client (title, email, phone, client_role, deleted)
	SELECT
		'title' || num AS name,
		'email' || num || '@' || (
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'gmail.com'
				WHEN 1 THEN 'mail.ru'
			 	WHEN 2 THEN 'yandex.ru'
			END
		) AS email,
		'phone' || num AS phone,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'SYSTEM_ADMIN'::client_role
				WHEN 1 THEN 'ADMIN'::client_role
			 	WHEN 2 THEN 'DISPATCHER'::client_role
			END
		) AS client_role,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;
