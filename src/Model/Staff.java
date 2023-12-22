package Model;

public class Staff {
    private String name;
    private int id;
    private Role role;

    // Constructor
    public Staff(String name, int id, Role role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
