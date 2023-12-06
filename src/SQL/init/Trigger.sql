-- 함수 정의
CREATE OR REPLACE FUNCTION update_number_of_games()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Client SET NumberOfGames = NumberOfGames + 1 WHERE ClientID = NEW.ClientID;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 트리거 생성
DROP TRIGGER IF EXISTS update_number_of_games_trigger ON Game;
CREATE TRIGGER update_number_of_games_trigger
AFTER INSERT ON Game
FOR EACH ROW
EXECUTE FUNCTION update_number_of_games();

-- 함수 정의
CREATE OR REPLACE FUNCTION update_tier_score()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Client SET TierScore = TierScore + NEW.Score WHERE ClientID = NEW.ClientID;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 트리거 생성
DROP TRIGGER IF EXISTS update_tier_score_trigger ON Game;
CREATE TRIGGER update_tier_score_trigger
AFTER INSERT ON Game
FOR EACH ROW
EXECUTE FUNCTION update_tier_score();
