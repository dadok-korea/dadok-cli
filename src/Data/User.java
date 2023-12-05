package Data;

public class User {
    private String userName;
    private String password;
    private int tierScore;
    private int gameCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
