DROP TABLE IF EXISTS client;
CREATE TABLE IF NOT EXISTS client
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
    email VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	phone VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	is_active BOOLEAN NOT NULL
);
