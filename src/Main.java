import Data.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        CommandInterface commandInterface = new CommandInterface();
        Query query = new Query();
        query.dbConnect();
        query.dbInit();

        while (true) {
            int mode = commandInterface.printMain();
            switch (mode) {
                case 1:
                    Client client = commandInterface.getClientId();
                    query.insertClient(client);
                    QuizSetList quizSetList = query.getQuizSet();
                    Game game = commandInterface.printGame(quizSetList, client);
                    query.insertGame(game);
                    break;
                case 2:
                    int rankMode = commandInterface.getRankMode();
                    List<Rank> rankList;
                    switch (rankMode) {
                        case 1:
                             rankList = query.getTimeRank();
                             commandInterface.printRank(rankList, rankMode);
                            break;
                        case 2:
                            rankList = query.getTierRank();
                            commandInterface.printRank(rankList, rankMode);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("다시 입력해주세요");
                    }
                    break;
                case 3:
                    String searchUserName = commandInterface.getSearchUserName();
                    Client user = query.getSearchUser(searchUserName);
                    List<Game> gameList = query.selectClientGameList(user.getClientId());
                    commandInterface.printSearchUser(user, gameList);
                    break;
                case 4:
                    System.out.println("정보보기");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("다시 입력해주세요");
            }
        }
    }
}