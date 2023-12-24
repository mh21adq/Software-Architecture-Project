package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StaffList {
    private ArrayList<Staff> staffList;

    public StaffList() {
        staffList = new ArrayList<>();
    }

    public void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String[] nameParts = parts[0].trim().split("\\s+");
                    Name name;

                    // Handling different name formats
                    if (nameParts.length == 1) {
                        // Only first name
                        name = new Name(nameParts[0]);
                    } else if (nameParts.length == 2) {
                        // First name and surname
                        name = new Name(nameParts[0], nameParts[1]);
                    } else {
                        // First name, middle name, and surname
                        name = new Name(nameParts[0], nameParts[1], nameParts[2]);
                    }

                    int id = Integer.parseInt(parts[1].trim());
                    Role role = Role.valueOf(parts[2].trim());

                    Staff staff = new Staff(id, name, role);
                    staffList.add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public Staff findStaffById(int id) {
        for (Staff staff : staffList) {
            if (staff.getStaffId() == id) {
                return staff;
            }
        }
        return null;
    }
}
