package MainApp;


import Controller.Manager;
import View.StaffGUI;

public class App {
    public static void main(String[] args) {
        Manager manager= new Manager();
        manager.readFromFile("/Users/mdnumanhussain/Documents/Software Architecture/SoftwareArchitectureProject/src/RunCompetitor.csv");
        manager.openStaffGUI();
    }
}
