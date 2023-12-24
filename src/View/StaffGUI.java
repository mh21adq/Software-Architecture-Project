package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class StaffGUI {
    private JFrame frame;
    private JTextField nameField, idField;
    private JButton openButton;
    private StaffList staffList;
    private Manager manager;

    public StaffGUI(Manager manager) {
        this.manager = manager;
        staffList = new StaffList();
        String filePath = "/Users/mdnumanhussain/Documents/Software Architecture/SoftwareArchitectureProject/src/RunStaff.csv";
        staffList.readFile(filePath);
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Staff Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        frame.setSize(400, 200);

        JLabel nameLabel = new JLabel("Staff Name:");
        nameField = new JTextField();
        JLabel idLabel = new JLabel("Staff ID:");
        idField = new JTextField();

        openButton = new JButton("Open Staff Window");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStaffVerification();
            }
        });

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(idLabel);
        frame.add(idField);
        frame.add(openButton);

        frame.setVisible(true);
    }

    private void handleStaffVerification() {
        String name = nameField.getText();
        int staffId = Integer.parseInt(idField.getText()); // Assuming ID is always a valid integer
        Staff staff = staffList.findStaffById(staffId);

        if (staff != null && staff.getName().equals(name)) {
            Role role = staff.getRole();
            updateUIForRole(role);
        } else {
            String message = staff != null ? "Name and ID do not match." : "Staff not found.";
            JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUIForRole(Role role) {
        frame.getContentPane().removeAll();
        frame.setLayout(new FlowLayout());

        switch (role) {
            case DATA_ENTRY:
                addDataEntryComponents();
                break;
            case OFFICIALS:
                addOfficialsComponents();
                break;
            case EMERGENCY_RESPONSE:
                addEmergencyResponseComponents();
                break;
            case REFEREE:
                addRefereeComponents();
                break;
        }

        frame.validate();
        frame.repaint();
    }

    private void addDataEntryComponents() {
        JButton btnReadFile = new JButton("Read from File");
        JButton btnSearchCompetitor = new JButton("Search Competitor");
        JButton btnEnterScore = new JButton("Enter Score");
        JButton btnViewAllCompetitors = new JButton("View All Competitors");
        JButton btnBrowseCategories = new JButton("Browse Competitors by Category");

        btnReadFile.addActionListener(createReadFileListener());
        btnSearchCompetitor.addActionListener(createSearchCompetitorListener());
        btnEnterScore.addActionListener(createEnterScoreListener());
        btnViewAllCompetitors.addActionListener(createViewAllCompetitorsListener());
        btnBrowseCategories.addActionListener(createBrowseCategoriesListener());

        frame.add(btnReadFile);
        frame.add(btnSearchCompetitor);
        frame.add(btnEnterScore);
        frame.add(btnViewAllCompetitors);
        frame.add(btnBrowseCategories);
    }

    private ActionListener createReadFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    String filePath = "src/" + fileName;
                    File file = new File(filePath);
                    if (!file.exists()) {
                        JOptionPane.showMessageDialog(frame, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        manager.readFromFile(filePath);
                        JOptionPane.showMessageDialog(frame, "Successfully read the file.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No file name entered.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }
    private ActionListener createSearchCompetitorListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String competitorNumber = JOptionPane.showInputDialog(frame, "Enter Competitor Number (Natural Number):");
                if (competitorNumber != null && !competitorNumber.trim().isEmpty()) {
                    try {
                        int competitorId = Integer.parseInt(competitorNumber);
                        Competitor competitor = manager.getCompetitor(competitorId);
                        if (competitor != null) {
                            JOptionPane.showMessageDialog(frame, "Competitor details: " + competitor.getFullDetails(), "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "No competitor found with ID: " + competitorId, "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid number. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No Competitor Number entered.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }

    private ActionListener createEnterScoreListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:");
                if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
                    int competitorId;
                    try {
                        competitorId = Integer.parseInt(competitorIdStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Manager manager = new Manager();
                    Competitor competitor = manager.searchCompetitor(competitorId);
                    if (competitor != null) {
                        int numScores = competitor instanceof Gamer ? 5 : (competitor instanceof IceSkater ? 4 : 0);
                        int[] scores = new int[numScores];

                        for (int i = 0; i < numScores; i++) {
                            String scoreStr;
                            int score;
                            do {
                                scoreStr = JOptionPane.showInputDialog(frame, "Enter score " + (i + 1) + " for " + competitor.getName().getFullName()+ ":");
                                try {
                                    score = Integer.parseInt(scoreStr);
                                    break; // Exit the loop if the score is valid
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(frame, "Invalid score format. Please enter a numeric score.", "Error", JOptionPane.ERROR_MESSAGE);
                                    score = -1; // Indicate an invalid score
                                }
                            } while (score < 0);

                            scores[i] = score;
                        }

                        competitor.setScores(scores); // Set the new scores
                        double overallScore = competitor.getOverallScore();
                        JOptionPane.showMessageDialog(frame, "Updated Overall Score for " + competitor.getName().getFullName() + ": " + overallScore, "Overall Score", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };
    }
    private ActionListener createViewAllCompetitorsListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String iceSkaters = manager.competitorsTable("ICE SKATING");
                String gamers = manager.competitorsTable("GAMING");

                JTextArea textArea = new JTextArea(iceSkaters+gamers);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                // Show the dialog with the JScrollPane
                JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);

            }


        };
    }
    private ActionListener createBrowseCategoriesListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"ICE SKATING", "GAMING"};
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        "Select a category to browse:",
                        "Browse Categories",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice != JOptionPane.CLOSED_OPTION) {
                    String selectedCategory = options[choice];
                    String competitors = manager.competitorsTable(selectedCategory);

                    JTextArea textArea = new JTextArea(competitors);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));
                    JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE); }
            }
        };
    }


    private void addOfficialsComponents() {
        // Add components and actions for OFFICIALS role
    }

    private void addEmergencyResponseComponents() {
        // Add components and actions for EMERGENCY_RESPONSE role
    }

    private void addRefereeComponents() {
        // Add components and actions for REFEREE role
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StaffGUI(new Manager()); // Assuming Manager is defined elsewhere
            }
        });
    }
}
