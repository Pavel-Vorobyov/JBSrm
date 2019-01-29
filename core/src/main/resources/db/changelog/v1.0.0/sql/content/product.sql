INSERT INTO product (product_details_id, amount, deed, productstate, deleted)
	SELECT
		num AS product_details_id,
		(
			CASE (RANDOM() * 3)::INT
				WHEN 0 THEN 50
				WHEN 1 THEN 100
				WHEN 2 THEN 150
				WHEN 3 THEN 200
			END
		) AS amount,
		(
			CASE (RANDOM() * 3)::INT
				WHEN 0 THEN 5
				WHEN 1 THEN 10
				WHEN 2 THEN 15
				WHEN 3 THEN 20
			END
		) AS deed,
		(
			CASE (RANDOM() * 3)::INT
				WHEN 0 THEN 'ACCEPTED'
				WHEN 1 THEN 'CHECK_COMPLETE'
				WHEN 2 THEN 'DELIVERED'
				WHEN 3 THEN 'LOST'
			END
		)::product_state AS productstate,
		(
			CASE (RANDOM() * 2)::INT
				WHEN 0 THEN true
				WHEN 1 THEN false
				WHEN 2 THEN true
			END
		) AS deleted
	FROM GENERATE_SERIES(1, 300) num;