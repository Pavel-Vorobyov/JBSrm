DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product
(
	id serial PRIMARY KEY,
    title VARCHAR (80) COLLATE pg_catalog."default" NOT NULL,
	required_type transport_type,
	amount INTEGER NOT NULL
);