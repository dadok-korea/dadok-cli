public class QuizSet {
    private String[][] options;
    private int[] answers;

    public QuizSet(String[][] options, int[] answers) {
        this.options = options;
        this.answers = answers;
    }

    public String[][] getOptions() {
        return options;
    }

    public int[] getAnswers() {
        return answers;
    }
}
