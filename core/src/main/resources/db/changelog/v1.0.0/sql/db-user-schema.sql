DROP TYPE IF EXISTS user_gender CASCADE;
CREATE TYPE user_gender AS ENUM ('MALE', 'FEMALE');

DROP TYPE IF EXISTS user_role CASCADE;
CREATE TYPE user_role AS ENUM ('ROLE_SYSTEM_ADMIN', 'ROLE_ADMIN', 'ROLE_DISPATCHER', 'ROLE_MANAGER', 'ROLE_DRIVER');

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users
(
	id serial PRIMARY KEY,
	email VARCHAR (80),
	password VARCHAR (300),
	name VARCHAR (80),
	surname VARCHAR (80),
	usergender user_gender,
	birthday DATE,
	passport_series VARCHAR (80),
	passport_issued_by VARCHAR (150),
	phone VARCHAR (80),
	company_id serial,
	userrole user_role,
	create_at DATE,
	deleted BOOLEAN
);

ALTER TABLE users ALTER COLUMN company_id DROP NOT NULL;

CREATE INDEX IF NOT EXISTS idx_fts_user ON users
  USING gin(as_tsvector(name, surname, email, phone));

INSERT INTO users (password, name, surname, usergender, birthday, passport_series, passport_issued_by, email, phone, company_id, userrole, create_at, deleted)
	SELECT
	    '$2a$10$UTLE.wVGcZbTPp6i1UbuJeKMu35hQyUsRxTd7tt6KaWrSQCPCN0.6' AS password,
		'name' || num AS name,
		'surname' || num AS surname,
		(
			CASE (RANDOM() * 1)::INT
				WHEN 0 THEN 'MALE'
				WHEN 1 THEN 'FEMALE'
			END
		)::user_gender AS usergender,
		(
			CURRENT_DATE::DATE
		) AS birthday,
		'series' || num AS passport_series,
		'issued_by' || num AS passport_issued_by,
		'mail' || num || '@' || (
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
				WHEN 0 THEN 'ROLE_SYSTEM_ADMIN'
				WHEN 1 THEN 'ROLE_ADMIN'
			 	WHEN 2 THEN 'ROLE_DISPATCHER'
			 	WHEN 3 THEN 'ROLE_MANAGER'
			 	WHEN 4 THEN 'ROLE_DRIVER'
			END
		)::user_role AS userrole,
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

INSERT INTO users (password, email, userrole, deleted)
    values ('$2a$10$UTLE.wVGcZbTPp6i1UbuJeKMu35hQyUsRxTd7tt6KaWrSQCPCN0.6', 'admin@gmail.com', 'ROLE_ADMIN', false);
INSERT INTO users (password, email, userrole, deleted)
    values ('$2a$10$UTLE.wVGcZbTPp6i1UbuJeKMu35hQyUsRxTd7tt6KaWrSQCPCN0.6', 'driver@gmail.com', 'ROLE_DRIVER', false);
