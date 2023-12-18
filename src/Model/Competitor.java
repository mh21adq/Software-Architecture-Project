package Model;

public abstract class Competitor {
    private static int lastNumber = 99;

    private int competitorNumber;
    private Name name;
    private String email;
    private int age;
    private String gender;
    private String country;
    private String level;
    public Competitor(Name name, String email, int age, String gender, String country) {
        this.competitorNumber = lastNumber++;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.country = country;
    }
}
