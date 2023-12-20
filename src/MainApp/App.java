package MainApp;


import Controller.Manager;
import View.StaffGUI;

public class App {
    public static void main(String[] args) {
        Manager manager= new Manager();
         manager.openStaffGUI();
    }
}
