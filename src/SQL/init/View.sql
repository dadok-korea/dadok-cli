-- GameTimeRankView 뷰 생성
DROP VIEW IF EXISTS GameTimeRankView;
CREATE VIEW GameTimeRankView AS
SELECT
    c.ClientID,
    g.Score,
    g.PlayTime,
    g.TimeStamp
FROM
    Client c
JOIN
    Game g ON c.ClientID = g.ClientID
ORDER BY
    g.Score DESC, -- Score를 내림차순으로 정렬
    g.PlayTime ASC; -- PlayTime을 오름차순으로 정렬

-- GameTierRankView 뷰 생성
DROP VIEW IF EXISTS GameTierRankView;
CREATE VIEW GameTierRankView AS
SELECT
    c.ClientID,
    c.TierScore,
    g.TimeStamp,
    c.NumberOfGames
FROM
    Client c
JOIN
    Game g ON c.ClientID = g.ClientID
ORDER BY
    c.TierScore DESC; -- TierScore를 내림차순으로 정렬

-- AverageScore + AllPlaytime
DROP VIEW IF EXISTS ClientGameStatistics;
CREATE VIEW ClientGameStatistics AS
SELECT C.ClientID, AVG(G.Score) AS AverageScore, SUM(G.PlayTime) AS TotalPlayTime
FROM Client C
JOIN Game G ON C.ClientID = G.ClientID
GROUP BY C.ClientID;
