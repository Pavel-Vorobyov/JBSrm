DROP TABLE IF EXISTS check_point;

CREATE TABLE IF NOT EXISTS check_point
(
	id serial PRIMARY KEY,
	waybill_id serial NOT NULL,
	title VARCHAR (255) COLLATE pg_catalog."default" NOT NULL,
	created_at DATE,
	deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS waybill;

CREATE TABLE IF NOT EXISTS waybill
(
	id SERIAL PRIMARY KEY,
	ttn_id SERIAL,
	transport_id SERIAL,
	created_at DATE,
	start_point_id SERIAL,
	end_point_id SERIAL,
	start_date DATE,
	end_date DATE,
	deleted BOOLEAN NOT NULL
);