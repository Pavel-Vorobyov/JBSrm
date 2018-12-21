DROP TYPE IF EXISTS user_gender CASCADE;
CREATE TYPE user_gender AS ENUM ('MALE', 'FEMALE');

DROP TYPE IF EXISTS user_role CASCADE;
CREATE TYPE user_role AS ENUM ('SYSTEM_ADMIN', 'ADMIN', 'DISPATCHER', 'MANAGER', 'DRIVER');

DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user"
(
	id serial PRIMARY KEY,
	login VARCHAR (80) UNIQUE NOT NULL,
	password VARCHAR (300) NOT NULL,
	name VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	surname VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	user_gender user_gender NOT NULL,
	birthday DATE NOT NULL,
	passport_id serial NOT NULL,
	email VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	phone VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	company_id serial NOT NULL,
	user_role user_role NOT NULL,
	create_at DATE NOT NULL,
	deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS passport;
CREATE TABLE IF NOT EXISTS passport
(
	id serial PRIMARY KEY,
	series VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	issued_by VARCHAR (150) COLLATE pg_catalog."default" NOT NULL,
	deleted BOOLEAN NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_user ON "user"
  USING gin(as_tsvector(name, surname, email, phone));

INSERT INTO "user" (login, password, name, surname, user_gender, birthday, email, phone, company_id, user_role, create_at, deleted)
	SELECT
	    'login' || num AS login,
	    'password' AS password,
		'name' || num AS name,
		'surname' || num AS surname,
		(
			CASE (RANDOM() * 1)::INT
				WHEN 0 THEN 'MALE'
				WHEN 1 THEN 'FEMALE'
			END
		)::user_gender AS user_gender,
		(
			CURRENT_DATE::DATE
		) AS birthday,
		'email' || num || '@' || (
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'gmail.com'
				WHEN 1 THEN 'mail.ru'
			 	WHEN 2 THEN 'yandex.ru'
			END
		) AS email,
		'phone' || num AS phone,
		num AS company_id,
		(
			CASE (RANDOM() * 4)::INT
				WHEN 0 THEN 'SYSTEM_ADMIN'
				WHEN 1 THEN 'ADMIN'
			 	WHEN 2 THEN 'DISPATCHER'
			 	WHEN 3 THEN 'MANAGER'
			 	WHEN 4 THEN 'DRIVER'
			END
		)::user_role AS user_role,
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

INSERT INTO passport (series, issued_by, deleted)
	SELECT
		'series' || num AS series,
		'issued_by' || num AS issued_by,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;