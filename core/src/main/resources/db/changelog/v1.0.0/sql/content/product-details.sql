INSERT INTO product_details (title, description, price, requiredtype, deleted)
	SELECT
		'title' || num AS title,
		'Description....' AS description,
		(
		    CASE (RANDOM() * 4)::INT
		        WHEN 0 THEN 50
		        WHEN 1 THEN 100
		        WHEN 2 THEN 150
		        WHEN 3 THEN 200
		        WHEN 4 THEN 250
		    END
		) AS price,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN 'COVERED_BODY'
				WHEN 1 THEN 'REFRIGERATOR'
				WHEN 2 THEN 'TANK'
			END
		)::required_type AS requiredtype,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;