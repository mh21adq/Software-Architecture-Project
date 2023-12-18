package View;

import Controller.Manager;
import Model.Competitor;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;

    public MainFrame() {
        super("MVC Pattern Example");
        cardLayout = new CardLayout();
        Form form = new Form();
        UserDetails userDetails = new UserDetails();
        Manager manager = new Manager();  // Assuming Manager can provide competitor data


        setLayout(cardLayout);

        add(form, "form");
        add(userDetails, "user details");

        form.viewUsers(e -> {
            manager.readFromFile("/Users/mdnumanhussain/Documents/Software Architecture/AssignmentPart-2/src/RunCompetitor.csv");

            form.createUser();
            java.util.List<Competitor> competitors = manager.getAllCompetitors();
            userDetails.setUsers(competitors);
            cardLayout.show(MainFrame.this.getContentPane(), "user details");
        });

        userDetails.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "form"));

        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
