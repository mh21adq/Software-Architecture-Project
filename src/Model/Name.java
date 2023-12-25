package Model;

public final class Name {
    private final String firstName;
    private final String middleName;
    private final String surname;

    public Name(String firstName) {
        this(firstName, null, null);
    }

    public Name(String firstName, String surname) {
        this(firstName, null, surname);
    }

    public Name(String firstName, String middleName, String surname) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName.trim();
        this.middleName = (middleName != null) ? middleName.trim() : null;
        this.surname = (surname != null) ? surname.trim() : null;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (middleName != null) {
            fullName.append(" ").append(middleName);
        }
        if (surname != null) {
            fullName.append(" ").append(surname);
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
