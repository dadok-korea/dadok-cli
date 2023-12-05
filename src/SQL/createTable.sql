CREATE TABLE IndependenceActivists (
                                       ID SERIAL PRIMARY KEY,
                                       Name VARCHAR(100) NULL,
                                       Link VARCHAR(255) NULL,

                                       ImageLink VARCHAR(255) NULL,
                                       Description TEXT NULL,
                                       BirthDate INTEGER NULL,
                                       DeathDate INTEGER NULL,
                                       VideoLink VARCHAR(255) NULL
);

CREATE TABLE proJapaneseActivator (
                                      no SERIAL PRIMARY KEY,
                                      부문 VARCHAR(4),
                                      분야 VARCHAR(4),
                                      분류 VARCHAR(10),
                                      성명 VARCHAR(4),
                                      생몰년 VARCHAR(20),
                                      친일반민족행위 TEXT,
                                      적용_법호 VARCHAR(100),
                                      선정_결정 TEXT
);

CREATE TABLE CombinedTable (
                               CombinedID SERIAL PRIMARY KEY,
                               성명 VARCHAR(10)[],
                               링크 VARCHAR(500),
                               이미지링크 VARCHAR(500),
                               행위_설명 VARCHAR(300),
                               생몰년 VARCHAR(50),
                               비디오링크 VARCHAR(255),
                               정답 VARCHAR(10)
);