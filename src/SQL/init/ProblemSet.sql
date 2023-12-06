CREATE TABLE IF NOT EXISTS ProblemSet (
ProblemSetID SERIAL PRIMARY KEY,
CombinedIDs SMALLINT[]
);

CREATE OR REPLACE FUNCTION fill_ProblemSet_table(repeat_count INT)
RETURNS void AS $$
DECLARE
i INT;
BEGIN
FOR i IN 1..repeat_count LOOP
INSERT INTO ProblemSet (CombinedIDs)
SELECT ARRAY_AGG(CombinedID)
FROM (
SELECT CombinedID
FROM CombinedTable
ORDER BY RANDOM()
LIMIT 10
) AS SubQuery;
END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT fill_ProblemSet_table(1000);
