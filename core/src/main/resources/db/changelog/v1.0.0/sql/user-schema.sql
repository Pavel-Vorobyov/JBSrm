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

INSERT INTO users (password, email, userrole, deleted)
    values ('$2a$10$UTLE.wVGcZbTPp6i1UbuJeKMu35hQyUsRxTd7tt6KaWrSQCPCN0.6', 'admin@gmail.com', 'ROLE_ADMIN', false);
INSERT INTO users (password, email, userrole, deleted)
    values ('$2a$10$UTLE.wVGcZbTPp6i1UbuJeKMu35hQyUsRxTd7tt6KaWrSQCPCN0.6', 'driver@gmail.com', 'ROLE_DRIVER', false);
