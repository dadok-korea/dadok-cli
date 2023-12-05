package Data;

public class Game {
    private String userName;
    private String timeStamp;
    private QuizSet quizSet;
    private Long solvedTime;
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public QuizSet getQuizSet() {
        return quizSet;
    }

    public void setQuizSet(QuizSet quizSet) {
        this.quizSet = quizSet;
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
