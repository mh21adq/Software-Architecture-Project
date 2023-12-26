package MainApp;
import Controller.Manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Manager manager = new Manager();
            manager.readFromFile("src/RunCompetitor.csv");

            // Create the main frame
            JFrame mainFrame = new JFrame("Competition Management System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(WIDTH,HEIGHT);
            mainFrame.setLayout(new BorderLayout());

            // Create a panel to hold the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3)); // Three columns for buttons

            // Create buttons for each option with styling
            JButton viewResultButton = createStyledButton("View Result");
            JButton competitorButton = createStyledButton("Competitor");
            JButton staffButton = createStyledButton("Staff");

            // Add action listeners to the buttons to open the respective GUIs
            viewResultButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open the Audience GUI when "View Result" button is clicked
                    manager.openAudienceGUI(manager);
                }
            });

            competitorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open the Competitor GUI when "Competitor" button is clicked
                    manager.openCompetitorGUI(manager);
                }
            });

            staffButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open the Staff GUI when "Staff" button is clicked
                    manager.openStaffGUI(manager);
                }
            });

            // Add buttons to the button panel
            buttonPanel.add(viewResultButton);
            buttonPanel.add(competitorButton);
            buttonPanel.add(staffButton);

            // Add the button panel to the main frame
            mainFrame.add(buttonPanel, BorderLayout.CENTER);

            // Display the main frame
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

    // Function to create a styled button
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204)); // Blue background color
        button.setForeground(Color.BLUE); // White text color
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 77, 153)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204)); // Restore original color on exit
            }
        });

        return button;
    }
}
