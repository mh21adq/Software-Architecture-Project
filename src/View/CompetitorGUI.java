package View;
import Model.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
public class CompetitorGUI {

    private JFrame frame;
    private JButton registerButton,btnSearchCompetitor;
    private Manager manager;

    public CompetitorGUI(Manager manager) {
        initializeGUI();
        this.manager=manager;
    }

    private void initializeGUI() {
        frame = new JFrame("Competitor Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Using BorderLayout
        frame.setSize(300, 150); // Reduced size

        // Define colors and fonts
        Color backgroundColor = new Color(245, 245, 245); // Light gray background
        Color buttonColor = new Color(100, 149, 237); // Cornflower blue for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12); // Slightly smaller font

        // Create a panel with padding for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // GridLayout for buttons
        buttonPanel.setBackground(backgroundColor);

        // Register button
        registerButton = createButton("Register", buttonColor, buttonFont);

        // Search button
        btnSearchCompetitor = createButton("Search", buttonColor, buttonFont);

        // Add buttons to the panel
        buttonPanel.add(registerButton);
        buttonPanel.add(btnSearchCompetitor);

        // Add the panel to the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Add action listeners
        registerButton.addActionListener(e -> openRegistrationForm());
        btnSearchCompetitor.addActionListener(e -> openSearchCompetitorDialog());

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
        // Logic to open the registration form
        new RegistrationForm(manager);
    }
    private void openSearchCompetitorDialog() {
        // Step 1: Get User Input for Category and Level
        JPanel panel = new JPanel(new GridLayout(0, 1));
        ButtonGroup categoryGroup = new ButtonGroup();
        JRadioButton gamingButton = new JRadioButton("Gaming");
        JRadioButton iceSkatingButton = new JRadioButton("Ice Skating");
        categoryGroup.add(gamingButton);
        categoryGroup.add(iceSkatingButton);
        panel.add(gamingButton);
        panel.add(iceSkatingButton);

        ButtonGroup levelGroup = new ButtonGroup();
        String[] levels = {"Beginner", "Intermediate", "Advanced", "Professional"};
        for (String level : levels) {
            JRadioButton levelButton = new JRadioButton(level);
            levelGroup.add(levelButton);
            panel.add(levelButton);
        }

        int result = JOptionPane.showConfirmDialog(frame, panel, "Select Category and Level", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return; // User cancelled the operation
        }

        // Determine selected category and level
        String selectedCategory = gamingButton.isSelected() ? "Gaming" : (iceSkatingButton.isSelected() ? "Ice Skating" : null);
        String selectedLevel = null;
        for (Enumeration<AbstractButton> buttons = levelGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                selectedLevel = button.getText();
                break;
            }
        }

        if (selectedCategory == null || selectedLevel == null) {
            JOptionPane.showMessageDialog(frame, "Please select both category and level.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: Check Competition Completion
        Manager manager = new Manager();
        boolean isComplete = manager.isCompetitionCompleted(selectedCategory, Level.valueOf(selectedLevel.toUpperCase()));
        if (!isComplete) {
            JOptionPane.showMessageDialog(frame, "Competition in " + selectedCategory + " (" + selectedLevel + ") is not complete.", "Competition Status", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Step 3: Search for Competitor
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);
        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Competitor competitor = manager.getCompetitor(competitorId);
                if (competitor != null) {
                    JOptionPane.showMessageDialog(frame, competitor.getFullDetails(), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




}
