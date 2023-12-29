package View;
import Controller.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;


public class StaffGUI {
    private JFrame frame;
    private JTextField nameField, idField;
    private final StaffList staffList;
    private final Manager manager;

    public StaffGUI(Manager manager) {
        this.manager = manager;
        staffList = new StaffList();
        String filePath = "src/RunStaff.csv";
        staffList.readFile(filePath);
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Staff Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for precise positioning
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);

        Color backgroundColor = new Color(230, 230, 250); // Light lavender
        Color labelColor = new Color(70, 130, 180); // Steel blue
        Font labelFont = new Font("Arial", Font.BOLD, 12);

        frame.getContentPane().setBackground(backgroundColor);

        // Title
        JLabel titleLabel = new JLabel("Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(labelColor);
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.anchor = GridBagConstraints.CENTER;
        frame.add(titleLabel, titleConstraints);

        // Staff Name Label and Field
        JLabel nameLabel = new JLabel("Staff Name:");
        nameLabel.setForeground(labelColor);
        nameLabel.setFont(labelFont);
        GridBagConstraints nameLabelConstraints = new GridBagConstraints();
        nameLabelConstraints.gridx = 0;
        nameLabelConstraints.gridy = 1;
        nameLabelConstraints.anchor = GridBagConstraints.WEST;
        frame.add(nameLabel, nameLabelConstraints);

        nameField = new JTextField();
        GridBagConstraints nameFieldConstraints = new GridBagConstraints();
        nameFieldConstraints.gridx = 1;
        nameFieldConstraints.gridy = 1;
        nameFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(nameField, nameFieldConstraints);

        // Staff ID Label and Field
        JLabel idLabel = new JLabel("Staff ID:");
        idLabel.setForeground(labelColor);
        idLabel.setFont(labelFont);
        GridBagConstraints idLabelConstraints = new GridBagConstraints();
        idLabelConstraints.gridx = 0;
        idLabelConstraints.gridy = 2;
        idLabelConstraints.anchor = GridBagConstraints.WEST;
        frame.add(idLabel, idLabelConstraints);

        idField = new JTextField();
        GridBagConstraints idFieldConstraints = new GridBagConstraints();
        idFieldConstraints.gridx = 1;
        idFieldConstraints.gridy = 2;
        idFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(idField, idFieldConstraints);

        // Open Staff Window Button
        JButton openButton = new JButton("Open Staff Window");
        GridBagConstraints openButtonConstraints = new GridBagConstraints();
        openButtonConstraints.gridx = 0;
        openButtonConstraints.gridy = 3;
        openButtonConstraints.gridwidth = 2;
        openButtonConstraints.anchor = GridBagConstraints.CENTER;
        openButton.addActionListener(e -> handleStaffVerification());
        frame.add(openButton, openButtonConstraints);

        frame.setVisible(true);
    }


    private void handleStaffVerification() {
        String name = nameField.getText();
        int staffId = Integer.parseInt(idField.getText()); // Assuming ID is always a valid integer
        Staff staff = staffList.findStaffById(staffId);

        if (staff != null && staff.getName().getFullName().equals(name)) {
            Role role = staff.getStaffRole();
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
            case DATA_ENTRY -> addDataEntryComponents();
            case OFFICIALS -> addOfficialsComponents();
            case EMERGENCY_RESPONSE -> addEmergencyResponseComponents();
            case REFEREE -> addRefereeComponents();
        }

        frame.validate();
        frame.repaint();
    }


    private void addDataEntryComponents() {
        JButton btnReadFile = createStyledButton("Read from File");
        JButton btnSearchCompetitor = createStyledButton("Search Competitor");
        JButton btnEnterScore = createStyledButton("Enter Score");
        JButton btnViewAllCompetitors = createStyledButton("View All Competitors");
        JButton btnBrowseCategories = createStyledButton("Browse Competitors by Category");
        JButton btnShowTopScorer = createStyledButton("Show Top Scorer");
        JButton btnExitApp = createStyledButton("Exit Application");

        btnReadFile.addActionListener(createReadFileListener());
        btnSearchCompetitor.addActionListener(createSearchCompetitorListener());
        btnEnterScore.addActionListener(createEnterScoreListener());
        btnViewAllCompetitors.addActionListener(createViewAllCompetitorsListener());
        btnBrowseCategories.addActionListener(createBrowseCategoriesListener());
        btnShowTopScorer.addActionListener(createShowTopScorerListener());
        btnExitApp.addActionListener(createExitAppListener());

        frame.add(btnReadFile);
        frame.add(btnSearchCompetitor);
        frame.add(btnEnterScore);
        frame.add(btnViewAllCompetitors);
        frame.add(btnBrowseCategories);
        frame.add(btnShowTopScorer);
        frame.add(btnExitApp);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font
        button.setBackground(new Color(0, 123, 255)); // Background color (Primary blue)
        button.setForeground(Color.BLUE); // Text color
        button.setFocusPainted(false); // Remove focus border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
        button.setPreferredSize(new Dimension(200, 40)); // Set preferred size

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(23, 162, 255)); // Lighter background color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255)); // Restore original background color on exit
            }
        });

        return button;
    }


    private ActionListener createReadFileListener() {
        return e -> {
            String defaultFilePath = "src/RunCompetitor.csv";
            Object[] options = {"RunCompetitor.csv", "Enter file path"};
            int choice = JOptionPane.showOptionDialog(frame,
                    "Choose an option:",
                    "File Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            String filePath;
            if (choice == 0) {  // RunCompetitor.csv selected
                filePath = defaultFilePath;
            } else if (choice == 1) {  // Enter file path selected
                filePath = JOptionPane.showInputDialog(frame, "Enter file path:");
                if (filePath == null || filePath.trim().isEmpty()) {
                    return;  // No file path entered, exit listener
                }
            } else {
                return;  // User closed the dialog or clicked Cancel, exit listener
            }

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
        };
    }

    private ActionListener createSearchCompetitorListener() {
        return e -> {
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
        };
    }

    private ActionListener createEnterScoreListener() {
        return e -> {
            String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:");
            if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
                int competitorId;
                try {
                    competitorId = Integer.parseInt(competitorIdStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Competitor competitor = manager.getCompetitor(competitorId);
                if (competitor != null) {
                    int numScores = competitor instanceof Gamer ? 5 : (competitor instanceof IceSkater ? 4 : 0);
                    int maxScore = competitor instanceof Gamer ? 5 : 6;
                    int[] scores = new int[numScores];

                    for (int i = 0; i < numScores; i++) {
                        boolean validScore = false;
                        while (!validScore) {
                            String scoreStr = JOptionPane.showInputDialog(frame, "Enter score " + (i + 1) + " for " + competitor.getName().getFullName() + ":");
                            try {
                                int score = Integer.parseInt(scoreStr);
                                if (score < 0 || score > maxScore) {
                                    throw new IllegalArgumentException("Score must be between 0 and " + maxScore + ".");
                                }
                                scores[i] = score;
                                validScore = true;
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Invalid score format. Please enter a numeric score.", "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    try {
                        competitor.setScores(scores); // Set the new scores
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method if the scores array is invalid
                    }

                    double overallScore = competitor.getOverallScore();
                    JOptionPane.showMessageDialog(frame, "Updated Overall Score for " + competitor.getName().getFullName() + ": " + overallScore, "Overall Score", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Competitor not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }


    private JScrollPane createScrollPaneForText(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        return scrollPane;
    }


    private ActionListener createViewAllCompetitorsListener() {
        return e -> {
            String iceSkaters = manager.competitorsTable("ICE SKATING");
            String gamers = manager.competitorsTable("GAMING");

            JScrollPane scrollPane = createScrollPaneForText(iceSkaters + gamers);
            JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);
        };
    }

    private ActionListener createBrowseCategoriesListener() {
        return e -> {
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

                JScrollPane scrollPane = createScrollPaneForText(competitors);
                JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    private ActionListener createShowTopScorerListener() {
        return e -> {
            // First, ask for the category
            String[] categories = {"Ice Skater", "Gamer"};
            int categoryChoice = JOptionPane.showOptionDialog(
                    frame,
                    "Select a category:",
                    "Category Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categories,
                    categories[0]);

            if (categoryChoice != JOptionPane.CLOSED_OPTION) {
                String selectedCategory = categories[categoryChoice];

                // Now, ask for the level
                Level[] levels = Level.values(); // Assuming Level is an enum
                Level selectedLevel = (Level) JOptionPane.showInputDialog(
                        frame,
                        "Select the level:",
                        "Level Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        levels,
                        levels[0]);

                if (selectedLevel != null) {
                    // Call the manager method to get the top scorer
                    Competitor topScorer = manager.highestScoringCompetitor(selectedCategory, selectedLevel);
                    if (topScorer != null) {
                        JOptionPane.showMessageDialog(frame, "Top Scorer in " + selectedCategory + " at level " + selectedLevel + ": " + topScorer.getFullDetails(), "Top Scorer", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No top scorer found for the selected category and level.", "No Top Scorer", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };
    }

    private ActionListener createExitAppListener() {
        return e-> {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to close the application and save the data?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Assuming Manager's writeToFile method is properly implemented
                    manager.generateFinalReport("competitor_report.txt");
                    frame.dispose(); // Close the GUI window
                }
        };
    }

    private void addOfficialsComponents() {
        //Officials can do all the work done by data entry staff
        addDataEntryComponents();
        JButton btnRegisterCompetitor = new JButton("Register Competitor");
        JButton btnRemoveCompetitor = new JButton("Remove Competitor");
        JButton btnUpdateCompetitorInfo = new JButton("Update Competitor Info");


        btnRegisterCompetitor.addActionListener(createRegisterCompetitorListener());
        btnRemoveCompetitor.addActionListener(createRemoveCompetitorListener());
        btnUpdateCompetitorInfo.addActionListener(createUpdateCompetitorInfoListener());

        frame.add(btnRegisterCompetitor);
        frame.add(btnRemoveCompetitor);
        frame.add(btnUpdateCompetitorInfo);

    }
    private ActionListener createRegisterCompetitorListener() {
        return e -> new RegistrationForm(manager);
    }

    private ActionListener createRemoveCompetitorListener() {
        return e->{
                String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID to remove:");
                if (competitorIdStr != null && !competitorIdStr.trim().isEmpty()) {
                    try {
                        int competitorId = Integer.parseInt(competitorIdStr);
                        boolean isRemoved = manager.removeCompetitor(competitorId);
                        if (isRemoved) {
                            JOptionPane.showMessageDialog(frame, "Competitor with ID " + competitorId + " has been removed.", "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "No competitor found with ID " + competitorId + ".", "Removal Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No Competitor ID entered.", "Operation Cancelled", JOptionPane.INFORMATION_MESSAGE);
                }
        };
    }

    private ActionListener createUpdateCompetitorInfoListener() {
        return e->{
                String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:");
                if (competitorIdStr == null || competitorIdStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No Competitor ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int competitorId;
                try {
                    competitorId = Integer.parseInt(competitorIdStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Competitor competitor = manager.getCompetitor(competitorId);
                if (competitor == null) {
                    JOptionPane.showMessageDialog(frame, "No competitor found with ID: " + competitorId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] updateOptions = {"Name", "Email", "Age", "Gender", "Country"};
                String selectedOption = (String) JOptionPane.showInputDialog(
                        frame,
                        "Select an attribute to update for Competitor ID " + competitorId + ":",
                        "Update Competitor Information",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        updateOptions,
                        updateOptions[0]
                );

                if (selectedOption != null && !selectedOption.isEmpty()) {
                    String newValue = JOptionPane.showInputDialog(frame, "Enter new " + selectedOption.toLowerCase() + ":");
                    if (newValue != null && !newValue.isEmpty()) {
                        try {
                            switch (selectedOption) {
                                case "Name":
                                    competitor.setName(new Name(newValue));
                                    break;
                                case "Email":
                                    competitor.setEmail(newValue);
                                    break;
                                case "Age":
                                    competitor.setAge(Integer.parseInt(newValue));
                                    break;
                                case "Gender":
                                   // competitor.setGender();
                                    break;
                                case "Country":
                                    competitor.setCountry(newValue);
                                    break;
                            }
                            JOptionPane.showMessageDialog(frame, "Competitor updated: " + competitor.getFullDetails(), "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input for " + selectedOption.toLowerCase() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        };
    }


    private void addEmergencyResponseComponents() {
        // Add components and actions for EMERGENCY_RESPONSE role
    }

    private void addRefereeComponents() {
        // Add components and actions for REFEREE role
    }


}
