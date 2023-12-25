package Model;

public class Staff {
    private Name name; // Use the Name class for name
    private final int staffId;
    private final Role staffRole;

    public Staff(Name name, int staffId, Role staffRole) {
        this.name = name;
        this.staffId = staffId;
        this.staffRole = staffRole;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getStaffId() {
        return staffId;
    }

    public Role getStaffRole() {
        return staffRole;
    }
}
