package Data;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Game {
    private String clientId;
    private Date timestamp;
    private int problemSetId;
    private Long solvedTime;
    private int score;
    private int[] selectedAnswers;

    public String getClientId() {
        return clientId;
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

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(int problemSetId) {
        this.problemSetId = problemSetId;
    }

    public int[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(int[] selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }
    public Long getSolvedTime() {
        return solvedTime;
    }

    public void setSolvedTime(Long solvedTime) {
        this.solvedTime = solvedTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
