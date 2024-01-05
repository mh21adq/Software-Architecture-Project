package Model.Staff;

import Model.Name;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The StaffList class represents a collection of staff members in the system.
 * It provides methods to read staff data from a file and search for staff members by their unique IDs.
 */
public class StaffList {
    private final ArrayList<Staff> staffList; // A list to store staff members.

    /**
     * Constructs a new StaffList object, initializing the list of staff members.
     */
    public StaffList() {
        staffList = new ArrayList<>();
    }

    /**
     * Reads staff data from a file and populates the staff list.
     *
     * @param filePath The path to the file containing staff data.
     */
    public void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String nameString = parts[0].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    Role role = Role.valueOf(parts[2].trim());

                    Name name;
                    String[] nameParts = nameString.split(" ");
                    if (nameParts.length == 1) {
                        name = new Name(nameParts[0]);
                    } else if (nameParts.length == 2) {
                        name = new Name(nameParts[0], nameParts[1]);
                    } else if (nameParts.length >= 3) {
                        name = new Name(nameParts[0], nameParts[1], nameParts[2]);
                    } else {
                        System.err.println("Invalid name format: " + nameString);
                        continue;
                    }

                    Staff staff = new Staff(name, id, role);
                    staffList.add(staff);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a staff member by their unique ID.
     *
     * @param id The unique ID of the staff member to search for.
     * @return The staff member with the specified ID, or null if not found.
     */
    public Staff findStaffById(int id) {
        for (Staff staff : staffList) {
            if (staff.getStaffId() == id) {
                return staff;
            }
        }
        return null;
    }
}
