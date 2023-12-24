package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class IceSkater extends Competitor {
    private static final int SCORES_ARRAY_SIZE = 4;
    private static final String CATEGORY = "ICE SKATING";

    private int[] scores;
    private Level level;

    public IceSkater(Name name, String email, int age, String gender, String country, Level level) {
        super(name, email, age, gender, country);
        setLevel(level); // Use the setter to apply validation
        this.scores = new int[SCORES_ARRAY_SIZE];
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        // Check if level is one of the specified values
        if (level == Level.BEGINNER || level == Level.INTERMEDIATE || level == Level.ADVANCED) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be either BEGINNER, INTERMEDIATE, or ADVANCED for an IceSkater.");
        }
    }

    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public double getOverallScore() {
        return calculateAverageScore(scores);
    }

    @Override
    public int[] getScoresArray() {
        return scores;
    }

    public void setScores(int[] scores) {
        if (scores == null || scores.length != SCORES_ARRAY_SIZE) {
            throw new IllegalArgumentException("Scores array must not be null and must have " + SCORES_ARRAY_SIZE + " elements.");
        }
        this.scores = scores;
    }

    @Override
    public String getFullDetails() {
        return super.getFullDetails() + "\nReceived scores: " + this.getScores() +
                "\nOverall score: " + getOverallScore();
    }

    @Override
    public String getShortDetails() {
        return super.getShortDetails() + " in category " + CATEGORY + ". Scores: " + this.getScores();
    }

    // Overloaded method specific to IceSkater
    public double getOverallScore(int top) {
        if (top > scores.length) {
            throw new IllegalArgumentException("Top scores requested exceeds the number of available scores.");
        }
        int[] sortedScores = Arrays.copyOf(scores, scores.length);
        Arrays.sort(sortedScores);
        return calculateAverageScore(Arrays.copyOfRange(sortedScores, 0, top));
    }

    private double calculateAverageScore(int[] scoresArray) {
        double sum = 0;
        for (int score : scoresArray) {
            sum += score;
        }
        double average = sum / scoresArray.length;
        return BigDecimal.valueOf(average).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
