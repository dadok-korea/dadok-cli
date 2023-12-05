package Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rank {
        private String userName;
        private Long solvedTime;
        private String timestamp;
        private int solvedNum;
        private int tierScore;
        private int gameCount;

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

        public int getSolvedNum() {
                return solvedNum;
        }

        public void setSolvedNum(int solvedNum) {
                this.solvedNum = solvedNum;
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

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public void setTimestamp(Date date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.timestamp = dateFormat.format(date);
        }

        public Long getSolvedTime() {
                return solvedTime;
        }

        public String getUserName() {
                return userName;
        }

        public String getTimestamp() {
                return timestamp;
        }
}
