CREATE TABLE  IF NOT EXISTS CombinedTable (
CombinedID SERIAL PRIMARY KEY,
성명 VARCHAR(10)[],
링크 VARCHAR(500),
이미지링크 VARCHAR(500),
행위_설명 VARCHAR(300),
생몰년 VARCHAR(50),
비디오링크 VARCHAR(255),
정답 VARCHAR(10)
);

CREATE OR REPLACE FUNCTION fill_combined_table(repeat_count INT)
RETURNS void AS $$
DECLARE
    i INT;
BEGIN
    FOR i IN 1..repeat_count LOOP
        INSERT INTO CombinedTable (성명, 링크, 이미지링크, 행위_설명, 생몰년, 비디오링크, 정답)
        SELECT
            temp.array_agg_성명 AS 성명,
            ia.Link AS 링크,
            ia.ImageLink AS 이미지링크,
            ia.Description AS 행위_설명,
            COALESCE(
                CASE
                    WHEN LENGTH(ia.BirthDate::text) = 8 AND LENGTH(ia.DeathDate::text) = 8 THEN
                        TO_CHAR(TO_TIMESTAMP(ia.BirthDate::text, 'YYYYMMDD'), 'YYYY-MM-DD') || ' - ' ||
                        TO_CHAR(TO_TIMESTAMP(ia.DeathDate::text, 'YYYYMMDD'), 'YYYY-MM-DD')
                    ELSE
                        NULL
                END,
                'Unknown'
            ) AS 생몰년,
            ia.VideoLink AS 비디오링크,
            ia.name AS 정답
        FROM
            (SELECT * FROM IndependenceActivists ORDER BY RANDOM() LIMIT 1) ia,
            (SELECT ARRAY_AGG(성명) FROM (SELECT 성명 FROM proJapaneseActivator ORDER BY RANDOM() LIMIT 4) subquery) AS temp(array_agg_성명)
        WHERE NOT EXISTS (
            SELECT 1 FROM CombinedTable
            WHERE CombinedTable.정답 = ia.name AND CombinedTable.성명 = temp.array_agg_성명
        );
    END LOOP;
END;
$$ LANGUAGE plpgsql;


SELECT fill_combined_table(1000);