package Data;

import java.util.List;

public class QuizSetList {
    private List<QuizSet> quizSetList;
    private Integer problemSetId;

    public List<QuizSet> getQuizSetList() {
        return quizSetList;
    }

    public void setQuizSetList(List<QuizSet> quizSetList) {
        this.quizSetList = quizSetList;
    }

    public Integer getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(Integer problemSetId) {
        this.problemSetId = problemSetId;
    }
}
