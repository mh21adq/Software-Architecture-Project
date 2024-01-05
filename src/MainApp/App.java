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
            mainFrame.setSize(800, 800);
            mainFrame.setLayout(new BorderLayout());

            // Customize the frame background
            mainFrame.getContentPane().setBackground(new Color(255, 255, 224)); // Light Yellow

            // Create a panel for buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
            buttonPanel.setBackground(new Color(173, 216, 230)); // Light Blue

            // Create buttons with colors and fonts
            JButton audienceButton = createCustomButton("Open Audience GUI");
            JButton competitorButton = createCustomButton("Open Competitor GUI");
            JButton staffButton = createCustomButton("Open Staff GUI");

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

    private static JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.blue);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }
}
