package Data;

public class QuizSet {
    private String[][] options;
    private int[] answers;

    public String[][] getOptions() {
        return options;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setOptions(String[][] options) {
        this.options = options;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }
}
