import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandInterface {
    Scanner scan = new Scanner(System.in);

     public int main() {
        System.out.println("===== 다음 중 독립운동가는? =====");
        System.out.println("1. 게임하기");
        System.out.println("2. 정보보기");
        System.out.print("입력 : ");
        int mode = Integer.parseInt(scan.nextLine());
        System.out.println("============================");
        return mode;
     }

    public String getUserName() {
        System.out.println("===== 닉네임을 입력해주세요 =====");
        System.out.print("입력 : ");
        String userName = scan.nextLine();
        System.out.println("============================");
        return userName;
    }

    public int playGame(QuizSet quizSet, String userName) {

        int score = 0;
        int questionsNum = 10;

        for (int i = 0; i < questionsNum; i++) {
            System.out.println("===== " + i+1 + ". 다음 중 독립운동가는? =====");

            for (int j = 0; j < quizSet.getOptions()[i].length; j++) {
                int quizNum = j+1;
                System.out.println(quizNum + ". " + quizSet.getOptions()[i][j]);
            }

            System.out.print("입력 : ");
            int select = Integer.parseInt(scan.nextLine());

            if (select == quizSet.getAnswers()[i]) {
                System.out.println("정답입니다!");
                score++;
            } else {
                System.out.println("오답입니다! 정답은 " + quizSet.getAnswers()[i] + "입니다!");
            }
        }

        System.out.println("Quiz completed!");
        System.out.println("Your score: " + score + "/" + questionsNum);
        return score;
    }

    private static void executeQuery(Connection connection, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                printResultSet(resultSet);
            } catch (SQLException sqlException) {
            }
        }
    }

    private static void executeQueryWithParams(Connection connection, String query, String param1, String param2) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, param1);
            preparedStatement.setObject(2, param2);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                printResultSet(resultSet);
            } catch (SQLException sqlException) {
            }
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        int numCount = 0;

        System.out.print("no" + "\t");
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
        }
        System.out.println();

        while (resultSet.next()) {
            numCount++;
            System.out.print(numCount + "\t");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }
}
