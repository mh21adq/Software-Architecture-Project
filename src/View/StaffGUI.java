package View;

import Controller.Manager;
import Model.Competitor;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StaffGUI {
    private JFrame frame;
    private JButton btnSearchCompetitor, btnAddScore, btnManageCompetitor, btnRegisterCompetitor,
            btnRemoveCompetitor, btnAllCompetitors,btnCompetitorInCategory,btnCompetitorInLevel;

    public StaffGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Staff Competitor Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(800, 600); // Set the window size

        btnSearchCompetitor = new JButton("Search Competitor");
        btnAddScore = new JButton("Add Scores");
        btnManageCompetitor = new JButton("Manage Competitor");
        btnRegisterCompetitor = new JButton("Register Competitor");
        btnRemoveCompetitor = new JButton("Remove Competitor");
        btnAllCompetitors = new JButton("All Competitors");
        btnCompetitorInCategory = new JButton("Competitors in Category");
        btnCompetitorInLevel = new JButton("Competitors in Level");


        frame.add(btnSearchCompetitor);
        frame.add(btnAddScore);
        frame.add(btnManageCompetitor);
        frame.add(btnRegisterCompetitor);
        frame.add(btnRemoveCompetitor);
        frame.add(btnAllCompetitors);
        frame.add(btnCompetitorInCategory);
        frame.add(btnCompetitorInLevel);

        // Add action listeners to buttons
        btnSearchCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchCompetitorDialog();
            }
        });
        btnAddScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddScoreDialog();
            }
        });
        btnManageCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageCompetitorDialog();
            }
        });

        btnRegisterCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterCompetitorDialog();
            }
        });
        btnAllCompetitors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAllCompetitorsDialog();
            }
        });
        btnRemoveCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveCompetitorDialog();
            }
        });
        btnCompetitorInCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCompetitorInCategoryDialog();
            }
        });
        btnCompetitorInLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCompetitorInLevelDialog();
            }
        });


        frame.setVisible(true);
    }

    private void openSearchCompetitorDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);

        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Manager manager = new Manager();
                Competitor competitor = manager.searchCompetitor(competitorId);
                if (competitor != null) {
                    // Using getFullDetails() to display competitor details
                    String details = competitor.getFullDetails();
                    JOptionPane.showMessageDialog(frame, details, "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openAddScoreDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);

        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Manager manager = new Manager();
                Competitor competitor = manager.searchCompetitor(competitorId);

                if (competitor != null) {
                    int numScores = competitor instanceof Gamer ? 5 : (competitor instanceof IceSkater ? 4 : 0);
                    int[] scores = new int[numScores];

                    for (int i = 0; i < numScores; i++) {
                        String scoreStr = JOptionPane.showInputDialog(frame, "Enter score " + (i + 1) + " for " + competitor.getName() + ":", "Input Score", JOptionPane.QUESTION_MESSAGE);
                        try {
                            scores[i] = Integer.parseInt(scoreStr);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(frame, "Invalid score format. Please enter a numeric score.", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Exit the method if invalid input is provided
                        }
                    }

                    competitor.setScores(scores); // Set the new scores

                    // Display the updated overall score
                    double overallScore = competitor.getOverallScore();
                    JOptionPane.showMessageDialog(frame, "Updated Overall Score for " + competitor.getName() + ": " + overallScore, "Overall Score", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openManageCompetitorDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);
        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Manager manager = new Manager();
                Competitor competitor = manager.searchCompetitor(competitorId);

                if (competitor != null) {
                    showManagementOptions(competitor);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showManagementOptions(Competitor competitor) {
        String[] options = {
                "Update Name",
                "Update Email",
                "Update Age",
                "Update Gender",
                "Update Country"
        };

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();

        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            group.add(radioButton);
            panel.add(radioButton);
        }

        int result = JOptionPane.showConfirmDialog(frame, panel, "Manage Competitor Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();

                if (button.isSelected()) {
                    handleCompetitorUpdate(competitor, button.getText());
                    break;
                }
            }
        }
    }

    private void handleCompetitorUpdate(Competitor competitor, String updateOption) {
        boolean updated = false;
        switch (updateOption) {
            case "Update Name":
                String newName = JOptionPane.showInputDialog(frame, "Enter new name:", "Update Name", JOptionPane.QUESTION_MESSAGE);
                if (newName != null && !newName.isEmpty()) {
                    competitor.setName(new Name(newName)); // Assuming Name is a class used in your Competitor class
                }
                updated = true;
                break;
            case "Update Email":
                String newEmail = JOptionPane.showInputDialog(frame, "Enter new email:", "Update Email", JOptionPane.QUESTION_MESSAGE);
                if (newEmail != null && !newEmail.isEmpty()) {
                    competitor.setEmail(newEmail);
                }
                updated = true;
                break;
            case "Update Age":
                String newAgeStr = JOptionPane.showInputDialog(frame, "Enter new age:", "Update Age", JOptionPane.QUESTION_MESSAGE);
                try {
                    int newAge = Integer.parseInt(newAgeStr);
                    competitor.setAge(newAge);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid age format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                updated = true;
                break;
            case "Update Gender":
                String newGender = JOptionPane.showInputDialog(frame, "Enter new gender:", "Update Gender", JOptionPane.QUESTION_MESSAGE);
                if (newGender != null && !newGender.isEmpty()) {
                    competitor.setGender(newGender);
                }
                updated = true;
                break;
            case "Update Country":
                String newCountry = JOptionPane.showInputDialog(frame, "Enter new country:", "Update Country", JOptionPane.QUESTION_MESSAGE);
                if (newCountry != null && !newCountry.isEmpty()) {
                    competitor.setCountry(newCountry);
                }
                updated = true;
                break;


        }
        if (updated) {
            showUpdatedDetails(competitor);
        }
    }

    private void showUpdatedDetails(Competitor competitor) {
        String updatedDetails = competitor.getFullDetails();
        JOptionPane.showMessageDialog(frame, updatedDetails, "Updated Competitor Details", JOptionPane.INFORMATION_MESSAGE);
    }


    private void openRegisterCompetitorDialog() {
        new RegistrationGUI();
    }

    private void openAllCompetitorsDialog() {
        Manager manager = new Manager();
        String allCompetitors = manager.printCompetitorsTable();

        // Create a JTextArea and put the text in it
        JTextArea textArea = new JTextArea(allCompetitors);
        textArea.setEditable(false); // Make it read-only
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Create a JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Set your preferred size

        // Show the dialog with the JScrollPane
        JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);
    }


    private void openRemoveCompetitorDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID to remove:", "Remove Competitor", JOptionPane.QUESTION_MESSAGE);

        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Manager manager = new Manager();
                Competitor competitor = manager.searchCompetitor(competitorId);

                if (competitor != null) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove " + competitor.getName() + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        manager.removeCompetitor(competitor);
                        JOptionPane.showMessageDialog(frame, "Competitor removed successfully.", "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void openCompetitorInCategoryDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();

        // Adding radio buttons for each category
        JRadioButton gamingButton = new JRadioButton("Gaming");
        JRadioButton iceSkatingButton = new JRadioButton("Ice Skating");
        group.add(gamingButton);
        group.add(iceSkatingButton);
        panel.add(gamingButton);
        panel.add(iceSkatingButton);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Select Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedCategory = gamingButton.isSelected() ? "Gaming" : (iceSkatingButton.isSelected() ? "Ice Skating" : null);

            if (selectedCategory != null) {
                Manager manager = new Manager();
                ArrayList<Competitor> competitorsInCategory = manager.getCompetitorsByCategory(selectedCategory);

                if (!competitorsInCategory.isEmpty()) {
                    StringBuilder competitorsDetails = new StringBuilder();
                    for (Competitor competitor : competitorsInCategory) {
                        competitorsDetails.append(competitor.getFullDetails()).append("\n");
                    }

                    JTextArea textArea = new JTextArea(competitorsDetails.toString());
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));

                    JOptionPane.showMessageDialog(frame, scrollPane, "Competitors in " + selectedCategory, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No competitors found in the category: " + selectedCategory, "No Competitors", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    private void openCompetitorInLevelDialog() {
        // First, let the user select a category
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        ButtonGroup categoryGroup = new ButtonGroup();

        JRadioButton gamingButton = new JRadioButton("Gaming");
        JRadioButton iceSkatingButton = new JRadioButton("Ice Skating");
        categoryGroup.add(gamingButton);
        categoryGroup.add(iceSkatingButton);
        categoryPanel.add(gamingButton);
        categoryPanel.add(iceSkatingButton);

        int categoryResult = JOptionPane.showConfirmDialog(frame, categoryPanel, "Select Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String selectedCategory = null;

        if (categoryResult == JOptionPane.OK_OPTION) {
            selectedCategory = gamingButton.isSelected() ? "Gaming" : (iceSkatingButton.isSelected() ? "Ice Skating" : null);
        }

        if (selectedCategory == null) return; // Exit if no category is selected

        // Next, let the user select a level
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        ButtonGroup levelGroup = new ButtonGroup();

        String[] levels = {"Beginner", "Intermediate", "Advanced", "Professional"};
        for (String level : levels) {
            JRadioButton levelButton = new JRadioButton(level);
            levelGroup.add(levelButton);
            levelPanel.add(levelButton);
        }

        int levelResult = JOptionPane.showConfirmDialog(frame, levelPanel, "Select Level", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String selectedLevel = null;

        for (Enumeration<AbstractButton> buttons = levelGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                selectedLevel = button.getText();
                break;
            }
        }

        if (selectedLevel == null) return; // Exit if no level is selected

        // Fetch and display competitors in the selected category and level
        Manager manager = new Manager();
        ArrayList<Competitor> competitorsInLevel = manager.searchCompetitorsByLevel(selectedCategory, Level.valueOf(selectedLevel.toUpperCase()));

        if (!competitorsInLevel.isEmpty()) {
            StringBuilder competitorsDetails = new StringBuilder();
            for (Competitor competitor : competitorsInLevel) {
                competitorsDetails.append(competitor.getFullDetails()).append("\n");
            }

            JTextArea textArea = new JTextArea(competitorsDetails.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(frame, scrollPane, "Competitors in " + selectedCategory + " - " + selectedLevel, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "No competitors found in the category: " + selectedCategory + " and level: " + selectedLevel, "No Competitors", JOptionPane.INFORMATION_MESSAGE);
        }
    }



}
