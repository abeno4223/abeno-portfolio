SELECT * FROM film;
SELECT * FROM rental;
SELECT * FROM inventory;
SELECT * FROM film_actor;
SELECT * FROM actor;


CREATE OR REPLACE FUNCTION first_plus_last (f VARCHAR(45), l VARCHAR(45))
RETURNS VARCHAR(91) 
LANGUAGE plpgsql
AS $$
DECLARE
	r VARCHAR(91);
BEGIN
	r = f || ' ' || l;
	RETURN r;
END;
$$;
SELECT first_plus_last( first_name, last_name ) AS name FROM actor;





--DROP TABLE detailed_report;
--DROP TABLE summary_report;
CREATE OR REPLACE FUNCTION generate_tables()
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
	CREATE TABLE detailed_report (
		film_id INT,
		title VARCHAR(255),
		actor_id SMALLINT,
		full_name VARCHAR(91),
		rental_id INT,
		inventory_id int
	);
	CREATE TABLE summary_report (
		full_name VARCHAR(91),
		times_rented INTEGER
	);
END;
$$;	

SELECT generate_tables();


SELECT * FROM detailed_report;
SELECT * FROM summary_report;


--DROP FUNCTION update_summary();
CREATE OR REPLACE FUNCTION update_summary()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM summary_report;
	INSERT INTO summary_report(full_name, times_rented)
	SELECT
		full_name,
		COUNT ( * ) AS Occurrences
		FROM detailed_report
		GROUP BY full_name
		ORDER BY Occurrences DESC;
	RETURN NEW;
END;
$$;

--DROP TRIGGER IF EXISTS trigger_update ON detailed_report;
CREATE TRIGGER trigger_update
AFTER INSERT OR UPDATE OR DELETE ON detailed_report
EXECUTE FUNCTION update_summary();

CALL fill_tables();
SELECT * FROM detailed_report;
SELECT * FROM summary_report;