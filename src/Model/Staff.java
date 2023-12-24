package Model;

public class Staff {
    private Name staffName;
    private final int staffId;
    private final Role staffRole;

    public Staff(int staffId, Name staffName, Role staffRole) {
        this.staffId = staffId;
        setStaffName(staffName); // Using setter for validation
        this.staffRole = staffRole;
    }

    public Name getStaffName() {
        return staffName;
    }

    public void setStaffName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        if (name.getMiddleName() == null && name.getSurname() == null) {
            this.staffName = new Name(name.getFirstName());
        } else if (name.getMiddleName() == null) {
            this.staffName = new Name(name.getFirstName(), name.getSurname());
        } else {
            this.staffName = new Name(name.getFirstName(), name.getMiddleName(), name.getSurname());
        }
    }
    public int getStaffId() {
        return staffId;
    }

    public Role getStaffRole() {
        return staffRole;
    }
}
