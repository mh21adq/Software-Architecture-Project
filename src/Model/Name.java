package Model;

public class Name {
    private String firstName;
    private String middleName;
    private String surname;

    public Name(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName.trim();
    }

    public Name(String firstName, String surname) {
        if (firstName == null || firstName.trim().isEmpty() || surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("First name and surname cannot be null or empty.");
        }
        this.firstName = firstName.trim();
        this.surname = surname.trim();
    }

    public Name(String firstName, String middleName, String surname) {
        if (firstName == null || firstName.trim().isEmpty() || middleName == null || middleName.trim().isEmpty() || surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("First name, middle name, and surname cannot be null or empty.");
        }
        this.firstName = firstName.trim();
        this.middleName = middleName.trim();
        this.surname = surname.trim();
    }
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
        StringBuilder fullName = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            fullName.append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(middleName);
        }
        if (surname != null && !surname.isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(surname);
        }
        return fullName.toString();
    }

    public String getInitials() {
        StringBuilder initials = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            initials.append(firstName.charAt(0));
        }
        if (middleName != null && !middleName.isEmpty()) {
            initials.append(middleName.charAt(0));
        }
        if (surname != null && !surname.isEmpty()) {
            initials.append(surname.charAt(0));
        }
        return initials.toString().toUpperCase();
    }
}
