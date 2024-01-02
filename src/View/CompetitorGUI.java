package View;
import Model.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;
public class CompetitorGUI {

    private JFrame frame;
    private final Manager manager;

    public CompetitorGUI(Manager manager) {
        initializeGUI();
        this.manager=manager;
    }

    private void initializeGUI() {
        JButton registerButton,btnSearchCompetitor,btnGenerateReport;
        frame = new JFrame("Competitor Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = screenSize.width - frame.getWidth();
        int y = (screenSize.height - frame.getHeight()) / 2;

        frame.setLocation(x, y);
        // Define colors and fonts
        Color backgroundColor = new Color(245, 245, 245);
        Color buttonColor = new Color(100, 149, 237);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        // Create a panel with padding for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(backgroundColor);

        registerButton = createButton("Register", buttonColor, buttonFont);

        btnSearchCompetitor = createButton("Search", buttonColor, buttonFont);

        btnGenerateReport = createButton("Print Reports", buttonColor, buttonFont);
        buttonPanel.add(registerButton);
        buttonPanel.add(btnSearchCompetitor);
        buttonPanel.add(btnGenerateReport);

        // Add the panel to the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Add action listeners
        registerButton.addActionListener(e -> openRegistrationForm());
        btnSearchCompetitor.addActionListener(e -> openSearchCompetitorDialog());
        btnGenerateReport.addActionListener(e -> openSearchCompetitorDialog());
        // Display the frame
        frame.setVisible(true);
    }

    private JButton createButton(String text, Color color, Font font) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private void openRegistrationForm() {
        new RegistrationForm(manager);
    }
    private void openSearchCompetitorDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);
        if (competitorIdStr == null || competitorIdStr.isEmpty()) {
            return;
        }

        try {
            int competitorId = Integer.parseInt(competitorIdStr);
            Competitor competitor = manager.getCompetitor(competitorId);
            if (competitor != null) {
                boolean competitionCompleted = manager.isCompetitionCompleted(competitor.getCategory(), competitor.getLevel());
                boolean scoresComplete = competitor.getScoreArray() != null && competitor.getScoreArray().length > 0;

                if (competitionCompleted && scoresComplete) {
                    JOptionPane.showMessageDialog(frame, competitor.getFullDetails(), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competition is not complete or scores are incomplete for this competitor.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
