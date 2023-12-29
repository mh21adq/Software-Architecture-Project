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
        double weightedSum = 0;
        double totalWeight = 0;
        double[] weights;

        if (this.level == Level.BEGINNER) {
            weights = new double[]{0.2, 0.3, 0.3, 0.2};
        } else if (this.level == Level.INTERMEDIATE) {
            weights = new double[]{0.15, 0.25, 0.35, 0.25};
        } else if (this.level == Level.ADVANCED) {
            weights = new double[]{0.1, 0.2, 0.4, 0.3};
        } else {
            throw new IllegalStateException("Unknown level: " + this.level);
        }

        for (int i = 0; i < SCORES_ARRAY_SIZE; i++) {
            weightedSum += scores[i] * weights[i];
            totalWeight += weights[i];
        }

        double weightedAverage = weightedSum / totalWeight;
        return BigDecimal.valueOf(weightedAverage).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }


    @Override
    public int[] getScoreArray() {
        return scores;
    }

    public void setScores(int[] scores) {
        if (scores == null) {
            throw new IllegalArgumentException("Scores array must not be null.");
        }
        if (scores.length != SCORES_ARRAY_SIZE) {
            throw new IllegalArgumentException("Scores array must have " + SCORES_ARRAY_SIZE + " elements.");
        }
        for (int score : scores) {
            if (score < 0 || score > 6) {
                throw new IllegalArgumentException("Each score must be between 0 and 6.");
            }
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


}
