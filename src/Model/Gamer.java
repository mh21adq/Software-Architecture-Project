package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Gamer extends Competitor {

    private static final int SCORES_ARRAY_SIZE = 5;
    private static final String CATEGORY = "GAMING";

    private int[] scores;
    private Level level;

    public Gamer(Name name, String email, int age, String gender, String country, Level level) {
        super(name, email, age, gender, country);
        setLevel(level);
        this.scores = new int[SCORES_ARRAY_SIZE];
    }

    @Override
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        // Ensure that the level is either NOVICE or EXPERT
        if (level == Level.NOVICE || level == Level.EXPERT) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be either NOVICE or EXPERT for a Gamer.");
        }
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public double getOverallScore() {
        double sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = sum / scores.length;
        return BigDecimal.valueOf(average).setScale(3, RoundingMode.HALF_UP).doubleValue();
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
                "\nOverall score: " + this.getOverallScore();
    }

    @Override
    public String getShortDetails() {
        return super.getShortDetails() + " in category " + CATEGORY + ". Scores: " + this.getScores();
    }
}
