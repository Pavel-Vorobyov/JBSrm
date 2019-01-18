INSERT INTO transport (title, bodytype, consumption, company_id, deleted)
	SELECT
	    'title' || num AS title,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'::transport_type
				WHEN 1 THEN 'REFRIGERATOR'::transport_type
				WHEN 2 THEN 'TANK'::transport_type
			END
		) AS bodytype,
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
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;