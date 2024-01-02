package Model;

/**
 * The Staff class represents personnel in the system with a name, unique staff ID, and a designated role.
 * Staff members can have various roles with different permissions and responsibilities.
 */
public class Staff {
    private Name name;
    private final int staffId;
    private final Role staffRole;

    /**
     * Constructs a new Staff object with the specified name, staff ID, and staff role.
     *
     * @param name      The name of the staff member.
     * @param staffId   The unique staff ID.
     * @param staffRole The role assigned to the staff member.
     */
    public Staff(Name name, int staffId, Role staffRole) {
        this.name = name;
        this.staffId = staffId;
        this.staffRole = staffRole;
    }

    /**
     * Retrieves the name of the staff member.
     *
     * @return The name of the staff member.
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the name of the staff member.
     *
     * @param name The new name of the staff member.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Retrieves the unique staff ID of the staff member.
     *
     * @return The unique staff ID.
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * Retrieves the role assigned to the staff member.
     *
     * @return The staff member's role.
     */
    public Role getStaffRole() {
        return staffRole;
    }
}
