DROP TYPE IF EXISTS check_point_status CASCADE;
CREATE TYPE check_point_status AS ENUM ('NOT_PASSED', 'PASSED');

create or replace function as_text(i check_point_status)
returns text as $$
begin
		return i::text;
end;
$$ language 'plpgsql' immutable;

DROP TABLE IF EXISTS check_point;
CREATE TABLE IF NOT EXISTS check_point
(
	id serial PRIMARY KEY,
	waybill_id serial NOT NULL,
	title VARCHAR (255) COLLATE pg_catalog."default" NOT NULL,
	lat double precision NOT NULL,
	lng double precision NOT NULL,
	check_point_status check_point_status NOT NULL,
	deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS waybill;
CREATE TABLE IF NOT EXISTS waybill
(
	id SERIAL PRIMARY KEY,
	ttn_id SERIAL,
	transport_id SERIAL,
	created_at DATE,
	start_date DATE,
	end_date DATE,
	deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS waybill_checkpoints;
CREATE TABLE IF NOT EXISTS waybill_checkpoints
(
	id serial PRIMARY KEY,
    waybill_id serial NOT NULL,
    checkpoint_id serial NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_fts_waybill_checkpoints ON waybill_checkpoints
  USING gin(as_tsvector(id::TEXT, waybill_id::TEXT, checkpoint_id::TEXT));

CREATE INDEX IF NOT EXISTS idx_fts_check_point ON check_point
  USING gin(as_tsvector(as_text(id), as_text(waybill_id), title, as_text(check_point_status), as_text(deleted)));

CREATE INDEX IF NOT EXISTS idx_fts_waybill ON waybill
  USING gin(as_tsvector(as_text(id), as_text(ttn_id),as_text(transport_id),as_text(created_at),as_text(start_date),as_text(end_date),as_text(deleted)));

insert into check_point (title, lat, lng, waybill_id, check_point_status,deleted)
	values ('check+point', 19.618997640855298, -83.6948828125, 1, 'PASSED', false);
insert into check_point (title, lat, lng, waybill_id,check_point_status,deleted)
	values ('check+point', 20.618997640855298, -84.6948828125, 1, 'PASSED', false);

insert into waybill (ttn_id, transport_id, created_at, start_date, end_date, deleted)
	values (1, 1, CURRENT_DATE::DATE, CURRENT_DATE::DATE, CURRENT_DATE::DATE, false);
insert into waybill_checkpoints (waybill_id, checkpoint_id) values (1, 1);
insert into waybill_checkpoints (waybill_id, checkpoint_id) values (1, 2);