import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Query {

    Connection connection;

    public void dbConnect() throws SQLException {
        loadEnvFromFile(".env");
        String url = "jdbc:postgresql://" + System.getProperty("DB_HOST") + ":" + System.getProperty("DB_PORT") + "/" + System.getProperty("DB_NAME");
        String username = System.getProperty("DB_USER");
        String password = System.getProperty("DB_PASSWORD");

        connection = DriverManager.getConnection(url, username, password);
    }

    public QuizSet makeQuizSet () throws SQLException {
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
        QuizSet quizSet = new QuizSet(options, answers);
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
}
