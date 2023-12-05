import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        CommandInterface commandInterface = new CommandInterface();
        Query query = new Query();
        query.dbConnect();
        String userName = "";

        while (true) {
            int mode = commandInterface.main();
            switch (mode) {
                case 1:
                    userName = commandInterface.getUserName();
                    QuizSet quizSet = query.makeQuizSet();
                    int score = commandInterface.playGame(quizSet, userName);
                    break;
                case 2:
                    System.out.println("정보보기");
                    break;
                default:
                    System.out.println("다시 입력해주세요");
            }
        }
    }
}
