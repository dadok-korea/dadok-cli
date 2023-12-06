package Data;

public class Client {
    private String clientId;
    private String password;
    private int tierScore;
    private int numOfGames;

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
}
