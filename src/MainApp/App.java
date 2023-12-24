package MainApp;
import Controller.Manager;
public class App {
    public static void main(String[] args) {
        Manager manager= new Manager();
        manager.openStaffGUI(manager);
        manager.openCompetitorGUI(manager);
        manager.openAudienceGUI(manager);
    }
}
