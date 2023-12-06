import Data.*;

import java.util.*;

public class CommandInterface {
    Scanner scan = new Scanner(System.in);

     public int printMain() {
         System.out.println("\n" +
                 "██████╗  █████╗ ██████╗  ██████╗ ██╗  ██╗\n" +
                 "██╔══██╗██╔══██╗██╔══██╗██╔═══██╗██║ ██╔╝\n" +
                 "██║  ██║███████║██║  ██║██║   ██║█████╔╝ \n" +
                 "██║  ██║██╔══██║██║  ██║██║   ██║██╔═██╗ \n" +
                 "██████╔╝██║  ██║██████╔╝╚██████╔╝██║  ██╗\n" +
                 "╚═════╝ ╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝\n" +
                 "\n");
        System.out.println("=========== 다음 중 독립운동가는? ===========");
        System.out.println("1. 게임하기");
        System.out.println("2. 순위보기");
        System.out.println("3. 유저검색");
        System.out.println("4. 정보보기");
        System.out.println("5. 종료하기");
        System.out.print("입력 : ");
        int mode = Integer.parseInt(scan.nextLine());
        System.out.println("========================================");
        return mode;
     }

    public Integer getRankMode() {
        System.out.println("================ 순위 정보 ================");
        System.out.println("1. 시간 순위");
        System.out.println("2. 티어 순위");
        System.out.println("3. 뒤로가기");
        System.out.print("입력 : ");
        int mode = Integer.parseInt(scan.nextLine());
        System.out.println("=========================================");
        return mode;
    }

    public String getSearchUserName() {
        System.out.println("================ 유저검색 ================");
        System.out.print("이름 입력 : ");
        String userName = scan.nextLine();
        System.out.println("========================================");
        return userName;
    }

    public void printSearchUser(Client client, List<Game> gameList) {
        System.out.println("================ 유저정보 ================");
        System.out.println("이름 : " + client.getClientId());
        System.out.println("티어 점수 : " + client.getTierScore());
        System.out.println("게임 수 : " + client.getNumOfGames());
        System.out.println("========================================");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n", "타임스탬프", "문제", "정답", "소요시간", "점수");
        for (Game game : gameList) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                    game.getTimestamp(),
                    game.getProblemSetId(),
                    Arrays.toString(game.getSelectedAnswers()),
                    game.getTypedSolvedTime(),
                    game.getScore()
                    );
        }
    }

    public void printRank(List<Rank> rankList, Integer mode) {
        switch (mode) {
            case 1:
                System.out.printf("%-15s %-15s %-15s %-25s%n", "유저명", "맞은 문제 수", "소요시간", "타임스탬프");
                System.out.println("====================================================================");
                for (Rank rank : rankList) {
                    System.out.printf("%-15s %-15s %-15s %-25s%n",
                            rank.getClientId(),
                            rank.getScore(),
                            rank.getTypedSolvedTime(),
                            rank.getTimestamp());
                }
                System.out.println("====================================================================");
                break;
            case 2:
                System.out.printf("%-15s %-15s %-15s %-25s%n", "유저명", "티어 점수", "최근 게임", "게임 수");
                System.out.println("====================================================================");
                for (Rank rank : rankList) {
                    System.out.printf("%-15s %-15s %-15s %-25s%n",
                            rank.getClientId(),
                            rank.getTierScore(),
                            rank.getTimestamp(),
                            rank.getNumOfGames());
                }
                System.out.println("====================================================================");
                break;
            default:
                break;
        }
    }

    public Client getClientId() {
         Client client = new Client();
        System.out.println("=========== 닉네임을 입력해주세요 ===========");
        System.out.print("닉네임 : ");
        String clientId = scan.nextLine();
        System.out.print("비밀번호 : ");
        String password = scan.nextLine();
        System.out.println("========================================");
        client.setClientId(clientId);
        client.setPassword(password);
        return client;
    }

    public Game printGame(QuizSetList quizSetList, Client client) {
         Game game = new Game();
        int score = 0;
        Date startTime = new Date();
        Date endTime;
        int num = 0;
        int [] selectedAnswers = new int[10];

       for (QuizSet quizSet : quizSetList.getQuizSetList()) {
           num++;
            System.out.println("===== " + num + ". 다음 중 독립운동가는? =====");

            for (int j = 0; j < 5; j++) {
                System.out.println((j+1) + ". " + quizSet.getOptions()[j]);
            }

            System.out.print("입력 : ");
            int select = Integer.parseInt(scan.nextLine());
            if(select < 1 || select > 5) {
                System.out.println("오답입니다! 정답은 " + quizSet.getAnswers() + "입니다!");
            }
            else {
                selectedAnswers[num-1] = select;
                if (Objects.equals(quizSet.getOptions()[select-1], quizSet.getAnswers())) {
                    System.out.println("정답입니다!");
                    score++;
                } else {
                    System.out.println("오답입니다! 정답은 " + quizSet.getAnswers() + "입니다!");
                }
            }
        }
        endTime = new Date();

        game.setSelectedAnswers(selectedAnswers);
        game.setClientId(client.getClientId());
        game.setScore(score);
        game.setProblemSetId(quizSetList.getProblemSetId());
        game.setTimestamp(endTime);
        game.setSolvedTime(endTime.getTime()-startTime.getTime());

        System.out.println("===== 게임 종료 =====");
        System.out.println("점수 : " + score + "/10");
        System.out.println("소요시간 : " + game.getTypedSolvedTime());
        return game;
    }
}
