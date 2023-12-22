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
                    String name = parts[0].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    Role role = Role.valueOf(parts[2].trim());
                    Staff staff = new Staff(name, id, role);
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
}
