CREATE TABLE IF NOT EXISTS Game(
GameID SERIAL PRIMARY KEY,
TimeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ClientID VARCHAR(30),
ProblemSetID SMALLINT,
PlayTime INTEGER,
SelectAnswer VARCHAR(30),
Score SMALLINT
);