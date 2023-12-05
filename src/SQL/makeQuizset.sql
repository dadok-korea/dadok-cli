INSERT INTO CombinedTable (성명, 링크, 이미지링크, 행위_설명, 생몰년, 비디오링크, 정답)
SELECT
    array_agg_성명 AS 성명,
    ia.Link AS 링크,
    ia.ImageLink AS 이미지링크,
    ia.Description AS 행위_설명,
    TO_CHAR(TO_TIMESTAMP(ia.BirthDate::text, 'YYYYMMDD'), 'YYYY-MM-DD') || ' - ' || TO_CHAR(TO_TIMESTAMP(ia.DeathDate::text, 'YYYYMMDD'), 'YYYY-MM-DD') AS 생몰년,
    ia.VideoLink AS 비디오링크,
    ia.name AS 정답
FROM
    (SELECT * FROM IndependenceActivists ORDER BY RANDOM() LIMIT 1) ia,
    (SELECT ARRAY_AGG(성명) FROM (SELECT 성명 FROM proJapaneseActivator ORDER BY RANDOM() LIMIT 4) subquery) AS temp(array_agg_성명)
WHERE NOT EXISTS (
    SELECT 1 FROM CombinedTable
    WHERE CombinedTable.정답 = ia.name AND CombinedTable.성명 = temp.array_agg_성명
);