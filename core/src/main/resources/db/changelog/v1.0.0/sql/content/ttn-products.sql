INSERT INTO ttn_products (ttn_id, product_id)
	SELECT
		num AS ttn_id,
		num AS product_id
	FROM GENERATE_SERIES(1, 300) num;