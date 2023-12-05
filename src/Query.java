import Data.Game;
import Data.QuizSet;
import Data.Rank;
import Data.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Query {
    private static Connection connection;
    private static Statement stmt;

    public void dbConnect() throws SQLException {
        loadEnvFromFile(".env");
        String url = "jdbc:postgresql://" + System.getProperty("DB_HOST") + ":" + System.getProperty("DB_PORT") + "/" + System.getProperty("DB_NAME");
        String username = System.getProperty("DB_USER");
        String password = System.getProperty("DB_PASSWORD");

        connection = DriverManager.getConnection(url, username, password);
        stmt = connection.createStatement();
    }

    public void dbInit() throws SQLException, IOException {
        try {
            executeQuery("createTable");
            executeQuery("insertIA");
            executeQuery("makeQuizset");
        } catch (SQLException e) {
        }
    }

    public List<Rank> getTimeRank() throws SQLException, IOException {
        List<Rank> ranks = new ArrayList<>();
        ResultSet resultSet = executeQuery("viewTimeRank");

        try {
            while (resultSet.next()) {
                Rank rank = new Rank();
                rank.setUserName(resultSet.getString("user_name"));
                rank.setSolvedTime(resultSet.getLong("solved_time"));
                rank.setSolvedNum(resultSet.getInt("solved_num"));
                rank.setTimestamp(resultSet.getDate("timestamp"));
                ranks.add(rank);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return ranks;
    }

    public List<Game> getUserGameList(String userName) throws SQLException, IOException {
        List<Game> gameList = new ArrayList<>();
        ResultSet resultSet = executeQuery("searchUserGameList", userName);

        try {
            while (resultSet.next()) {
                Game game = new Game();
                QuizSet quizSet = new QuizSet();
                game.setUserName(resultSet.getString("user_name"));
                game.setSolvedTime(resultSet.getLong("solved_time"));
                game.setTimeStamp(resultSet.getString("time_Stamp"));
                game.setScore(resultSet.getInt("score"));

                game.setQuizSet(quizSet);
                gameList.add(game);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return gameList;
    }

    public List<Rank> getTierRank() throws SQLException, IOException {
        List<Rank> ranks = new ArrayList<>();
        ResultSet resultSet = executeQuery("viewTimeRank");

        try {
            while (resultSet.next()) {
                Rank rank = new Rank();
                rank.setUserName(resultSet.getString("user_name"));
                rank.setSolvedTime(resultSet.getLong("solved_time"));
                rank.setSolvedNum(resultSet.getInt("solved_num"));
                rank.setTimestamp(resultSet.getDate("timestamp"));
                ranks.add(rank);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return ranks;
    }

    public User getSearchUser(String searchUserName) throws SQLException, IOException {
        ResultSet resultSet = executeQuery("searchUser", searchUserName);
        User user = new User();

        try {
            while (resultSet.next()) {
                user.setUserName(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("password"));
                user.setGameCount(resultSet.getInt("game_count"));
                user.setTierScore(resultSet.getInt("tier_score"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return user;
    }

    public void insertRank (Rank rank) throws SQLException, IOException {
        List<Rank> ranks = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery(parsingQuery("selectRank"));

        try {
            while (resultSet.next()) {
                if(Objects.equals(resultSet.getString("user_name"), rank.getUserName())) {
                    if(resultSet.getLong("solved_time") > rank.getSolvedTime() && resultSet.getInt("solved_num") <= rank.getSolvedNum()) {
                        rank.setUserName(resultSet.getString("user_name"));
                        rank.setSolvedTime(resultSet.getLong("solved_time"));
                        rank.setTimestamp(resultSet.getDate("timestamp"));
                        ranks.add(rank);
                    }
                }
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        executeQuery("insertRank", rank.getUserName(), rank.getSolvedTime(), rank.getTimestamp());
    }

    public QuizSet getQuizSet () throws SQLException {
        String[][] options = {
                {"Paris", "Rome", "Berlin", "Madrid"},
                {"Elephant", "Giraffe", "Blue Whale", "Lion"},
                {"Paris", "Rome", "Berlin", "Madrid"},
                {"Elephant", "Giraffe", "Blue Whale", "Lion"},
                {"Paris", "Rome", "Berlin", "Madrid"},
                {"Elephant", "Giraffe", "Blue Whale", "Lion"},
                {"Paris", "Rome", "Berlin", "Madrid"},
                {"Elephant", "Giraffe", "Blue Whale", "Lion"},
                {"Paris", "Rome", "Berlin", "Madrid"},
                {"Elephant", "Giraffe", "Blue Whale", "Lion"},
        };

        int[] answers = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2};
        QuizSet quizSet = new QuizSet();
        quizSet.setOptions(options);
        quizSet.setAnswers(answers);
        return quizSet;
    }

    private static void loadEnvFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    System.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String parsingQuery(String fileName) throws IOException {
        Path path = Path.of("src/SQL/", fileName + ".sql");
        String query = Files.readString(path, StandardCharsets.UTF_8);
        return query;
    }

    private static ResultSet executeQuery(String fileName, Object... values) throws SQLException, IOException {
        Path path = Path.of("src/SQL/", fileName + ".sql");
        String query = Files.readString(path, StandardCharsets.UTF_8);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            System.out.println(preparedStatement);

            try (ResultSet resultSet = stmt.executeQuery(preparedStatement.toString()+";")) {
                return resultSet;
            } catch (SQLException sqlException) {
                return null;
            }
        }
    }
}
