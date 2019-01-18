insert into check_point (title, lat, lng, waybill_id, checkpointstatus,deleted)
	values ('check+point', 19.618997640855298, -83.6948828125, 1, 'PASSED', false);
insert into check_point (title, lat, lng, waybill_id,checkpointstatus,deleted)
	values ('check+point', 20.618997640855298, -84.6948828125, 1, 'PASSED', false);

insert into waybill (ttn_id, transport_id, created_at, start_date, end_date, deleted)
	values (1, 1, CURRENT_DATE::DATE, CURRENT_DATE::DATE, CURRENT_DATE::DATE, false);
insert into waybill_checkpoints (waybill_id, checkpoint_id) values (1, 1);
insert into waybill_checkpoints (waybill_id, checkpoint_id) values (1, 2);