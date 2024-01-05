package Model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An abstract class representing a competitor.
 */
public abstract class Competitor {
    private static int lastNumber = 100;
    private  int competitorNumber;
    private Name name;
    private String email;
    private int age;
    private String gender;
    private String country;

    /**
     * Initializes a new competitor with the specified attributes.
     *
     * @param name    The competitor's name.
     * @param email   The competitor's email address.
     * @param age     The competitor's age.
     * @param gender  The competitor's gender.
     * @param country The competitor's country.
     */
    public Competitor(Name name, String email, int age, String gender, String country) {
        this.competitorNumber = lastNumber++;
        this.setName(name);
        this.setEmail(email);
        this.setAge(age);
        this.setGender(gender);
        this.setCountry(country);
    }

    /**
     * Sets the competitor's number and updates the static counter to the provided competitor number.
     * This method is intended for use when reading competitor data from a file where competitor numbers
     * are pre-assigned. It ensures that the next auto-generated competitor number continues from the
     * last assigned number to maintain uniqueness.
     *
     * @param competitorNumber The pre-assigned unique identifier for the competitor.
     */
    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
        lastNumber = competitorNumber+1;
    }
    /**
     * Gets the competitor's unique number.
     *
     * @return The competitor's number.
     */
    public int getCompetitorNumber() {
        return competitorNumber;
    }

    /**
     * Gets the competitor's name.
     *
     * @return The competitor's name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the competitor's name.
     *
     * @param name The competitor's name.
     */
    public void setName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        if (name.getMiddleName() == null && name.getSurname() == null) {
            this.name = new Name(name.getFirstName());
        } else if (name.getMiddleName() == null) {
            this.name = new Name(name.getFirstName(), name.getSurname());
        } else {
            this.name = new Name(name.getFirstName(), name.getMiddleName(), name.getSurname());
        }
    }

    /**
     * Gets the competitor's email address.
     *
     * @return The competitor's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the competitor's email address.
     *
     * @param email The competitor's email address.
     */
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email;
    }

    /**
     * Gets the competitor's age.
     *
     * @return The competitor's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the competitor's age.
     *
     * @param age The competitor's age.
     */
    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive and non-zero.");
        }
        this.age = age;
    }

    /**
     * Gets the competitor's gender.
     *
     * @return The competitor's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the competitor's gender.
     *
     * @param gender The competitor's gender.
     */
    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty.");
        }
        this.gender = gender;
    }

    /**
     * Gets the competitor's country.
     *
     * @return The competitor's country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the competitor's country.
     *
     * @param country The competitor's country.
     */
    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty.");
        }
        this.country = country;
    }

    /**
     * Gets the overall score of the competitor.
     *
     * @return The overall score.
     */
    public abstract double getOverallScore();

    /**
     * Sets the scores for the competitor.
     *
     * @param scores The scores to set.
     */
    public abstract void setScores(int[] scores);

    /**
     * Gets the category of the competitor.
     *
     * @return The category.
     */
    public abstract String getCategory();

    /**
     * Gets the level of the competitor.
     *
     * @return The level.
     */
    public abstract Level getLevel();

    /**
     * Gets the scores of the competitor as an array.
     *
     * @return The scores array.
     */
    public abstract int[] getScoreArray();

    /**
     * Gets the full details of the competitor.
     *
     * @return The full details.
     */
    public String getFullDetails() {
        return "\nCompetitor Number: " + this.getCompetitorNumber() + "\n" +
                "Name: " + this.name.getFullName() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "Gender: " + this.getGender() + "\n" +
                "Age: " + this.getAge() + "\n" +
                "Country: " + this.getCountry() + "\n";
    }

    /**
     * Gets the short details of the competitor.
     *
     * @return The short details.
     */
    public String getShortDetails() {
        return "\nCN " + this.competitorNumber +
                " (" + this.name.getInitials() + ")" +
                " has an overall score of " + this.getOverallScore();
    }

    /**
     * Formats the scores as a string.
     *
     * @return The formatted scores.
     */
    protected String formatScores() {
        return Arrays.stream(this.getScoreArray())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
