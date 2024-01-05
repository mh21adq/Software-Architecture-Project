package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Represents a Gamer competitor.
 */
public class Gamer extends Competitor {
    private static final int SCORES_ARRAY_SIZE = 5;
    private int[] scores;
    private Level level;

    /**
     * Initializes a new Gamer competitor with the specified attributes.
     *
     * @param name    The competitor's name.
     * @param email   The competitor's email address.
     * @param age     The competitor's age.
     * @param gender  The competitor's gender.
     * @param country The competitor's country.
     * @param level   The level of the Gamer.
     */
    public Gamer(Name name, String email, int age, String gender, String country, Level level) {
        super(name, email, age, gender, country);
        setLevel(level);
        this.scores = new int[SCORES_ARRAY_SIZE];
    }

    /**
     * Gets the level of the Gamer.
     *
     * @return The level.
     */
    @Override
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the level of the Gamer.
     *
     * @param level The level to set.
     */
    public void setLevel(Level level) {
        if (level == Level.NOVICE || level == Level.EXPERT) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be either NOVICE or EXPERT for a Gamer.");
        }
    }

    /**
     * Gets the category of the competitor.
     *
     * @return The category.
     */
    @Override
    public Category getCategory() {
        return Category.GAMING;
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
            if (score < 0 || score > 5) {
                throw new IllegalArgumentException("Each score must be between 0 and 5.");
            }
        }
        this.scores = scores;
    }

    /**
     * Calculates the overall score of the Gamer.
     *
     * @return The overall score.
     */
    @Override
    public double getOverallScore() {
        int scoresToConsider;
        if (this.level == Level.NOVICE) {
            scoresToConsider = Math.min(3, scores.length);
        } else {
            scoresToConsider = scores.length;
        }

        int[] sortedScores = Arrays.copyOf(scores, scores.length);
        Arrays.sort(sortedScores);

        double sum = 0;
        for (int i = sortedScores.length - 1; i >= sortedScores.length - scoresToConsider; i--) {
            sum += sortedScores[i];
        }

        double average = sum / scoresToConsider;

        return BigDecimal.valueOf(average).setScale(3, RoundingMode.HALF_UP).doubleValue();
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
