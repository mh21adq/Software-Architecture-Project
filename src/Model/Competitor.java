package Model;

public abstract class Competitor {
    private static int lastNumber = 100;

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
    public int getCompetitorNumber() {
        return competitorNumber;
    }


    public String getName() {
        return this.name.getFullName();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public abstract double getOverallScore() ;
    public abstract void setScores(int[] scores);
    public abstract String getCategory();
    public abstract Level getLevel();

    public String getFullDetails() {
        String details="\n*****************************************************\n"+
                "Competitor Number "+ this.competitorNumber+",name "+this.name.getFullName() +",country " +this.getCountry()+".\n"+
                this.name.getFirstName()+" is a "+this.getLevel()+ " aged "+this.getAge()+".";
        return details;
    }
    public String getShortDetails() {
        String shortDetails = "CN " + this.competitorNumber +
                " (" + name.getInitials() + ")" +
                " has an overall score of " + this.getOverallScore();
        return shortDetails;
    }



}
