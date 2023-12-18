package Model;
public class Name {
    private String firstName="";
    private String middleName="";
    private String surname="";

    // Constructors
    public Name(String firstName) {
        this.firstName = firstName;
    }

    public Name(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public Name(String firstName, String middleName, String surname) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
    }



    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return this.firstName + " " + this.middleName + " " + this.surname;
    }


}
