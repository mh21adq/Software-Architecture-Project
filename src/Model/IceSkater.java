package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an Ice Skater competitor.
 */
public class IceSkater extends Competitor {
    private static final int SCORES_ARRAY_SIZE = 4;
    private static final String CATEGORY = "ICE SKATING";

    private int[] scores;
    private Level level;

    /**
     * Initializes a new Ice Skater competitor with the specified attributes.
     *
     * @param name    The competitor's name.
     * @param email   The competitor's email address.
     * @param age     The competitor's age.
     * @param gender  The competitor's gender.
     * @param country The competitor's country.
     * @param level   The level of the Ice Skater.
     */
    public IceSkater(Name name, String email, int age, String gender, String country, Level level) {
        super(name, email, age, gender, country);
        setLevel(level); // Use the setter to apply validation
        this.scores = new int[SCORES_ARRAY_SIZE];
    }

    /**
     * Gets the level of the Ice Skater.
     *
     * @return The level.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the level of the Ice Skater.
     *
     * @param level The level to set.
     */
    public void setLevel(Level level) {
        // Check if level is one of the specified values
        if (level == Level.BEGINNER || level == Level.INTERMEDIATE || level == Level.ADVANCED) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be either BEGINNER, INTERMEDIATE, or ADVANCED for an IceSkater.");
        }
    }

    /**
     * Gets the category of the competitor.
     *
     * @return The category.
     */
    @Override
    public String getCategory() {
        return CATEGORY;
    }

    /**
     * Calculates the overall score of the Ice Skater.
     *
     * @return The overall score.
     */
    @Override
    public double getOverallScore() {
        double weightedSum = 0;
        double[] weights;
        // Weights are put in such a way so that it cannot get over 5;
        if (this.level == Level.BEGINNER) {
            weights = new double[]{1.0, 0.9, 0.8, 0.7};
        } else if (this.level == Level.INTERMEDIATE) {
            weights = new double[]{0.6, 0.5, 0.3, 0.2};
        } else if (this.level == Level.ADVANCED) {
            weights = new double[]{0.125, 0.975, 0.99, 0.5};
        } else {
            throw new IllegalStateException("Unknown level: " + this.level);
        }
        for (int i = 0; i < SCORES_ARRAY_SIZE; i++) {
            weightedSum += scores[i] * weights[i];
        }

        double weightedAverage = weightedSum / SCORES_ARRAY_SIZE;
        return BigDecimal.valueOf(weightedAverage).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Gets the array of scores for the competitor.
     *
     * @return The scores array.
     */
    @Override
    public int[] getScoreArray() {
        return scores;
    }

    /**
     * Sets the scores for the competitor.
     *
     * @param scores The scores to set.
     */
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

    /**
     * Gets the full details of the competitor.
     *
     * @return The full details.
     */
    @Override
    public String getFullDetails() {
        return super.getFullDetails() +
                "Category: " + this.getCategory() + "\n" +
                "Level: " + this.getLevel() + "\n" +
                "Received Scores: " + this.formatScores() + "\n" +
                "Overall Score: " + this.getOverallScore() + "\n";
    }
}
