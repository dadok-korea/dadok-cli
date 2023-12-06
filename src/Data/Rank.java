package Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rank {
        private String clientId;
        private Long solvedTime;
        private String timestamp;
        private int score;
        private int tierScore;
        private int gameCount;
        private int numOfGames;

        public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
        }

        public int getNumOfGames() {
                return numOfGames;
        }

        public void setNumOfGames(int numOfGames) {
                this.numOfGames = numOfGames;
        }

        public int getTierScore() {
                return tierScore;
        }

        public void setTierScore(int tierScore) {
                this.tierScore = tierScore;
        }

        public int getGameCount() {
                return gameCount;
        }

        public void setGameCount(int gameCount) {
                this.gameCount = gameCount;
        }

        public int getScore() {
                return score;
        }

        public void setScore(int score) {
                this.score = score;
        }

        public String getTypedSolvedTime() {
                if (solvedTime < 0) {
                        throw new IllegalArgumentException("Duration must be a non-negative number.");
                }

                long seconds = solvedTime / 1000;
                long milliSeconds = solvedTime % 1000;

                StringBuilder solvedTimeFormat = new StringBuilder();
                solvedTimeFormat.append(seconds).append(".");
                solvedTimeFormat.append(milliSeconds).append(" 초");

                return solvedTimeFormat.toString().trim();
        }

        public void setSolvedTime(Long paramMS) {
                this.solvedTime = paramMS;
        }

        public void setTimestamp(Date date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.timestamp = dateFormat.format(date);
        }

        public Long getSolvedTime() {
                return solvedTime;
        }

        public String getTimestamp() {
                return timestamp;
        }

        public String getClientId() {
                return clientId;
        }

        public void setClientId(String clientId) {
                this.clientId = clientId;
        }
}
