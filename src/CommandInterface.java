import Data.Game;
import Data.QuizSet;
import Data.Rank;
import Data.User;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommandInterface {
    Scanner scan = new Scanner(System.in);

     public int printMain() {
        System.out.println("===== 다음 중 독립운동가는? =====");
        System.out.println("1. 게임하기");
        System.out.println("2. 순위보기");
        System.out.println("3. 유저검색");
        System.out.println("4. 정보보기");
        System.out.println("5. 종료하기");
        System.out.print("입력 : ");
        int mode = Integer.parseInt(scan.nextLine());
        System.out.println("============================");
        return mode;
     }

    public Integer getRankMode() {
        System.out.println("===== 순위 정보 =====");
        System.out.println("1. 시간 순위");
        System.out.println("2. 티어 순위");
        System.out.println("3. 뒤로가기");
        System.out.print("입력 : ");
        int mode = Integer.parseInt(scan.nextLine());
        System.out.println("============================");
        return mode;
    }

    public String getSearchUserName() {
        System.out.println("===== 유저검색 =====");
        System.out.print("이름 입력 : ");
        String userName = scan.nextLine();
        System.out.println("============================");
        return userName;
    }

    public void printSearchUser(User user, List<Game> gameList) {
        System.out.println("===== 유저정보 =====");
        System.out.println("이름 : " + user.getUserName());
        System.out.println("티어 점수 : " + user.getTierScore());
        System.out.println("게임 수 : " + user.getGameCount());
        System.out.println("============================");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n", "타임스탬프", "문제", "정답", "소요시간", "점수");
        for (Game game : gameList) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                    game.getTimeStamp(),
                    game.getQuizSet().getOptions(),
                    game.getQuizSet().getAnswers(),
                    game.getSolvedTime(),
                    game.getScore()
                    );
        }
    }

    public void printRank(List<Rank> rankList, Integer mode) {
        switch (mode) {
            case 1:
                System.out.printf("%-15s %-15s %-15s %-25s%n", "유저명", "맞은 문제 수", "소요시간", "타임스탬프");
                System.out.println("====================");
                for (Rank rank : rankList) {
                    System.out.printf("%-15s %-15s %-15s %-25s%n",
                            rank.getUserName(),
                            rank.getSolvedNum(),
                            rank.getTypedSolvedTime(),
                            rank.getTimestamp());
                }
                System.out.println("============================");
                break;
            case 2:
                System.out.printf("%-15s %-15s %-15s %-25s%n", "유저명", "티어 점수", "게임 수", "최근 게임");
                System.out.println("====================");
                for (Rank rank : rankList) {
                    System.out.printf("%-15s %-15s %-15s %-25s%n",
                            rank.getUserName(),
                            rank.getTierScore(),
                            rank.getGameCount(),
                            rank.getTimestamp());
                }
                System.out.println("============================");
                break;
            default:
                break;
        }
    }

    public String getUserName() {
        System.out.println("===== 닉네임을 입력해주세요 =====");
        System.out.print("입력 : ");
        String userName = scan.nextLine();
        System.out.println("============================");
        return userName;
    }

    public Rank printGame(QuizSet quizSet, String userName) {

        int score = 0;
        int questionsNum = 10;
        Date startTime = new Date();
        Date endTime;

        for (int i = 0; i < questionsNum; i++) {
            System.out.println("===== " + (i+1) + ". 다음 중 독립운동가는? =====");

            for (int j = 0; j < quizSet.getOptions()[i].length; j++) {
                System.out.println((j+1) + ". " + quizSet.getOptions()[i][j]);
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
        endTime = new Date();

        Rank rank = new Rank();
        rank.setUserName(userName);
        rank.setTimestamp(endTime);
        rank.setSolvedTime(endTime.getTime()-startTime.getTime());
        rank.setSolvedNum(score);

        System.out.println("게임 종료!");
        System.out.println("점수 : " + score + "/" + questionsNum);
        System.out.println("소요시간 : " + rank.getTypedSolvedTime());
        return rank;
    }
}
