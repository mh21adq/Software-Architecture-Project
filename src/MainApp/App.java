package MainApp;


import Controller.Manager;
public class App {
    public static void main(String[] args) {
        Manager manager= new Manager();
         manager.openStaffGUI();
         manager.openCompetitorGUI();
         manager.openAudienceGUI(manager);
    }
}
