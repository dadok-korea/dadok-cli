import Data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Query {
    private static Connection connection;

    public void dbConnect() throws SQLException {
        loadEnvFromFile();
        String url = "jdbc:postgresql://" + System.getProperty("DB_HOST") + ":" + System.getProperty("DB_PORT") + "/" + System.getProperty("DB_NAME");
        String username = System.getProperty("DB_USER");
        String password = System.getProperty("DB_PASSWORD");

        connection = DriverManager.getConnection(url, username, password);
    }

    public void dbInit() throws SQLException, IOException {

        String[] sqlFiles = {
                "IndependenceActivists",
                "proJapaneseActivator",
                "CombinedTable",
                "ProblemSet",
                "Indexing",
                "Game",
                "Client",
                "View",
                "Trigger",
//                "TestQueries"
        };

        for (String sqlFile : sqlFiles) {
            executeQuery("init/" + sqlFile);
        }
    }

    public void insertClient(Client client) throws SQLException, IOException {
        executeQuery( "logic/InsertClient", client.getClientId(), client.getPassword());
    }

    public List<Game> selectClientGameList(String userName) throws SQLException, IOException {
        List<Game> gameList = new ArrayList<>();
        List<Map<String, Object>> resultSet = executeQuery( "logic/SelectSearchUserGame", userName);

        if(resultSet != null) {
            for (int i = 0; i < resultSet.size(); i++) {
                Game game = new Game();
                game.setTimestamp((Date) resultSet.get(i).get("timestamp"));
                game.setProblemSetId((Integer) resultSet.get(i).get("problemsetid"));
                game.setSolvedTime(Long.valueOf(resultSet.get(i).get("playtime").toString()));
                String cleanedString = resultSet.get(i).get("selectanswer").toString().replaceAll("[\\[\\] ]", "");
                String[] stringArray = cleanedString.split(",");
                int[] intArray = new int[stringArray.length];
                for (int j = 0; j < stringArray.length; j++) {
                    intArray[i] = Integer.parseInt(stringArray[i].trim());
                }
                game.setSelectedAnswers(intArray);
                game.setScore((Integer) resultSet.get(i).get("score"));
                gameList.add(game);
            }
        }

        return gameList;
    }

    public List<Rank> getTimeRank() throws SQLException, IOException {
        List<Rank> ranks = new ArrayList<>();
        List<Map<String, Object>> resultSet = executeQuery( "logic/SelectTimeRank");

        if(resultSet != null) {
            for(int i = 0; i < resultSet.size(); i++) {
                Rank rank = new Rank();
                rank.setClientId((String) resultSet.get(i).get("clientid"));
                rank.setScore((Integer) resultSet.get(i).get("score"));
                rank.setSolvedTime(Long.valueOf(resultSet.get(i).get("playtime").toString()));
                rank.setTimestamp((Date) resultSet.get(i).get("timestamp"));
                ranks.add(rank);
            }
        }
        return ranks;
    }

    public List<Rank> getTierRank() throws SQLException, IOException {
        List<Rank> ranks = new ArrayList<>();
        List<Map<String, Object>> resultSet = executeQuery( "logic/SelectTierRank");

        if(resultSet != null) {
            for (int i = 0; i < resultSet.size(); i++) {
                Rank rank = new Rank();
                rank.setClientId((String) resultSet.get(i).get("clientid"));
                rank.setTierScore((Integer) resultSet.get(i).get("tierscore"));
                rank.setTimestamp((Date) resultSet.get(i).get("timestamp"));
                rank.setNumOfGames((Integer) resultSet.get(i).get("numberofgames"));
                ranks.add(rank);
            }
        }

        return ranks;
    }

    public Client getSearchUser(String searchUserName) throws SQLException, IOException {
        List<Map<String, Object>> resultSet = executeQuery( "logic/SelectSearchUser", searchUserName);
        Client client = new Client();

        if(resultSet != null) {
            for (int i = 0; i < resultSet.size(); i++) {
                client.setClientId((String) resultSet.get(i).get("clientid"));
                client.setPassword((String) resultSet.get(i).get("password"));
                client.setTierScore((Integer) resultSet.get(i).get("tierscore"));
                client.setNumOfGames((Integer) resultSet.get(i).get("numberofgames"));
            }
        }
        return client;
    }

    public void insertGame (Game game) throws SQLException, IOException {
        Timestamp timestamp = new Timestamp(game.getTimestamp().getTime());
        executeQuery( "logic/InsertGame", timestamp, game.getClientId(), game.getProblemSetId(), game.getSolvedTime(), Arrays.toString(game.getSelectedAnswers()), game.getScore());
    }

    public QuizSetList getQuizSet () throws SQLException, IOException {
        QuizSetList result = new QuizSetList();
        List<QuizSet> quizSetList = new ArrayList<>();
        List<Map<String, Object>> resultSet = executeQuery( "logic/SelectCombinedids");
        List<Integer> integerList = new ArrayList<>();

        result.setProblemSetId((Integer) resultSet.get(0).get("problemsetid"));

        String[] parts = resultSet.get(0).get("combinedids").toString().replaceAll("[{}]", "").split(",");
        for (String part : parts) {
            integerList.add(Integer.parseInt(part.trim()));
        }

        for (Integer combinedid : integerList) {
            List<Map<String, Object>> resultSetList = executeQuery( "logic/SelectProblemSet", combinedid);

            if(resultSetList != null) {
                for (int i = 0; i < resultSetList.size(); i++) {
                    QuizSet quizSet = new QuizSet();

                    List<String> stringList = new ArrayList<>(List.of(resultSetList.get(i).get("성명").toString().replaceAll("[{}]", "").split(",\\s*")));
                    stringList.add((String) resultSetList.get(i).get("정답"));
                    Collections.shuffle(stringList);
                    String[] shuffledArray = stringList.toArray(new String[0]);

                    quizSet.setOptions(shuffledArray);
                    quizSet.setAnswers((String) resultSetList.get(i).get("정답"));
                    quizSet.setLink((String) resultSetList.get(i).get("링크"));
                    quizSet.setImgLink((String) resultSetList.get(i).get("이미지링크"));
                    quizSet.setVidLink((String) resultSetList.get(i).get("비디오링크"));
                    quizSet.setPeriod((String) resultSetList.get(i).get("생몰년"));
                    quizSet.setWhat((String) resultSetList.get(i).get("행위_설명"));
                    quizSetList.add(quizSet);
                }
            }
        }

        result.setQuizSetList(quizSetList);
        return result;
    }

    private static void loadEnvFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
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
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static List<Map<String, Object>> executeQuery(String fileName, Object... values) throws SQLException, IOException {
        String query = parsingQuery(fileName);
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    resultList.add(row);
                }
            } catch (SQLException sqlException) {
                return null;
            }
        }
        return resultList;
    }
}
