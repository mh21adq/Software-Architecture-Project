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


    public ArrayList<Staff> getStaffList() {
        return staffList;
    }
    public Staff findStaffById(int id) {
        for (Staff staff : staffList) {
            if (staff.getStaffId()== id) {
                return staff;
            }
        }
        return null;
    }
}
