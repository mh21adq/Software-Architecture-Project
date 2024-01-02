package Model;

/**
 * Represents a person's name with optional first name, middle name, and surname components.
 */
public final class Name {
    private final String firstName;
    private final String middleName;
    private final String surname;

    /**
     * Constructs a name with only a first name.
     *
     * @param firstName The first name.
     */
    public Name(String firstName) {
        this(firstName, null, null);
    }

    /**
     * Constructs a name with a first name and a surname.
     *
     * @param firstName The first name.
     * @param surname   The surname.
     */
    public Name(String firstName, String surname) {
        this(firstName, null, surname);
    }

    /**
     * Constructs a name with a first name, middle name, and surname.
     *
     * @param firstName   The first name.
     * @param middleName  The middle name.
     * @param surname     The surname.
     */
    public Name(String firstName, String middleName, String surname) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName.trim();
        this.middleName = (middleName != null) ? middleName.trim() : null;
        this.surname = (surname != null) ? surname.trim() : null;
    }

    /**
     * Gets the first name.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return The middle name, or null if not provided.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets the middle name.
     *
     * @return The middle name, or null if not provided.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the full name combining first name, middle name, and surname.
     *
     * @return The full name.
     */
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

    /**
     * Gets the initials of the name in uppercase.
     *
     * @return The initials.
     */
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
