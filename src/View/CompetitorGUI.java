package View;
import Model.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        frame.setLayout(new FlowLayout()); // Layout for the main window
        frame.setSize(450, 800);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = frame.getSize().width;
        int height = frame.getSize().height;
        int xPosition = screenSize.width - width; // This will position the frame at the right edge of the screen
        int yPosition = (screenSize.height - height) / 2; // This will center the frame vertically

        // Set the location of the frame to the calculated position
        frame.setLocation(xPosition, yPosition);

        // Register button
        registerButton = new JButton("Register Competitor");
        btnSearchCompetitor=new JButton("Search Competitor");
        frame.add(registerButton);
        frame.add(btnSearchCompetitor);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });
        btnSearchCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchCompetitorDiaglog();
            }
        });


        frame.setVisible(true);
    }

    private void openRegistrationForm() {
        // Logic to open the registration form
        new RegistrationForm(manager);
    }
    private void openSearchCompetitorDiaglog() {
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
