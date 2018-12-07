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
	is_active BOOLEAN NOT NULL
);
