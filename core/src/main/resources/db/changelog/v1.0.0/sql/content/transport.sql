INSERT INTO transport (title, bodytype, consumption, company_id, transportstate, deleted)
	SELECT
	    'title' || num AS title,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'
				WHEN 1 THEN 'REFRIGERATOR'
				WHEN 2 THEN 'TANK'
			END
		)::transport_type AS bodytype,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 50
				WHEN 1 THEN 100
				WHEN 2 THEN 150
			END
		) AS consumption,
		num AS company_id,
																										 (
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'FREE'
				WHEN 1 THEN 'FREE'
				WHEN 2 THEN 'BUSY'
			END
		)::transport_state AS transportstate,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted

	FROM GENERATE_SERIES(1, 300) num;