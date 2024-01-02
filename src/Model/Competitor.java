package Model;

import java.util.Arrays;
import java.util.stream.Collectors;
public abstract class Competitor {
    private static int lastNumber = 100;
    private final int competitorNumber;
    private Name name;
    private String email;
    private int age;
    private  String gender;
    private  String country;

    public Competitor(Name name, String email, int age, String gender, String country) {
        this.competitorNumber = lastNumber++;
        this.setName(name);
        this.setEmail(email);
        this.setAge(age);
        this.setGender(gender);
        this.setCountry(country);
    }
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
    public double getOverallScore() { return 5; }
    public abstract void setScores(int[] scores);
    public abstract String getCategory();
    public abstract Level getLevel();
    public abstract int[] getScoreArray();

    public String getFullDetails() {
        return "\nCompetitor Number: " + this.getCompetitorNumber() + "\n" +
                "Name: " + this.name.getFullName() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "Gender: " + this.getGender() + "\n"+
                "Age: " + this.getAge() + "\n"+
                "Country: " + this.getCountry() + "\n" ;
    }

    public String getShortDetails() {
        return "\nCN " + this.competitorNumber +
                " (" + this.name.getInitials() + ")" +
                " has an overall score of " + this.getOverallScore();
    }

    //Extra method to format the scores only used by child classes;
    protected String formatScores() {
        String formattedScores = Arrays.stream(this.getScoreArray())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
        return formattedScores;
    }

}
