package MainApp;

import Controller.Manager;
import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Manager manager = new Manager();
            manager.readFromFile("src/RunCompetitor.csv");

            // Create the main application window
            JFrame mainFrame = new JFrame("Competition Management System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 800); // Set the size of the main window
            mainFrame.setLayout(new BorderLayout()); // Set the layout

            // Create a panel for buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column

            // Create buttons
            JButton audienceButton = new JButton("Open Audience GUI");
            JButton competitorButton = new JButton("Open Competitor GUI");
            JButton staffButton = new JButton("Open Staff GUI");

            // Add action listeners to buttons
            audienceButton.addActionListener(e -> manager.openAudienceGUI(manager));
            competitorButton.addActionListener(e -> manager.openCompetitorGUI(manager));
            staffButton.addActionListener(e -> manager.openStaffGUI(manager));

            // Add buttons to the panel
            buttonPanel.add(audienceButton);
            buttonPanel.add(competitorButton);
            buttonPanel.add(staffButton);

            // Add the panel to the main frame
            mainFrame.add(buttonPanel, BorderLayout.CENTER);

            // Center the main frame on the screen
            mainFrame.setLocationRelativeTo(null);

            // Display the main frame
            mainFrame.setVisible(true);
        });
    }
}
