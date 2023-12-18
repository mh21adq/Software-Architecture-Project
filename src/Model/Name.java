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


}
