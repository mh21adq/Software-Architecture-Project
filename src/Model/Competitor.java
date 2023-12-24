package Model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Abstract class representing a competitor.
 */
public abstract class Competitor {
    private static int lastNumber = 100;
    private final int competitorNumber;
    private Name name;
    private String email;
    private int age;
    private  String gender;
    private  String country;

    /**
     * Constructs a new Competitor instance.
     *
     * @param name    The name of the competitor.
     * @param email   The email address of the competitor.
     * @param age     The age of the competitor.
     * @param gender  The gender of the competitor.
     * @param country The country of the competitor.
     */
    public Competitor(Name name, String email, int age, String gender, String country) {
        this.competitorNumber = lastNumber++;
        this.setName(name);
        this.setEmail(email);
        this.setAge(age);
        this.setGender(gender);
        this.setCountry(country);
    }

    // Getters and Setters

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public Name getName() {
        return name;
    }

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive and non-zero.");
        }
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty.");
        }
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty.");
        }
        this.country = country;
    }
    // Abstract methods
    public abstract double getOverallScore();
    public abstract void setScores(int[] scores);
    public abstract String getCategory();
    public abstract Level getLevel();
    public abstract int[] getScoresArray();

    // Method for getting scores in a String format
    public String getScores() {
        int[] scoresArray = getScoresArray();
        if (scoresArray == null) {
            return "";
        }
        return Arrays.stream(scoresArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    public String getFullDetails() {
        return "\n\nCompetitor Number " + this.competitorNumber +
                ", Name " + this.name.getFullName() +
                ", Country " + this.getCountry() +
                ".\n" + this.name.getFirstName() +
                " is a " + this.getLevel() +
                " aged " + this.getAge() + ".";
    }

    public String getShortDetails() {
        return "\n\nCN " + this.competitorNumber +
                " (" + this.name.getInitials() + ")" +
                " has an overall score of " + this.getOverallScore();
    }
}
