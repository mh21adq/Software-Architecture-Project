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
            btnRemoveCompetitor, btnAllCompetitors;

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
        frame.add(btnAllCompetitors);


        frame.add(btnSearchCompetitor);
        frame.add(btnAddScore);
        frame.add(btnManageCompetitor);
        frame.add(btnRegisterCompetitor);
        frame.add(btnRemoveCompetitor);
        frame.add(btnAllCompetitors);

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
}
