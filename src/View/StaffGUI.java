package View;
import Controller.*;
import Model.*;
import Model.Participant.Category;
import Model.Participant.Level;
import Model.Participant.Competitor;
import Model.Participant.Gamer;
import Model.Participant.IceSkater;
import Model.Staff.Role;
import Model.Staff.Staff;
import Model.Staff.StaffList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The StaffGUI class creates the graphical user interface for staff management in a competition system.
 * It offers a window with functionalities tailored to staff roles, such as data entry, officials, emergency response,
 * and referees. The class is responsible for handling staff sign-in and providing the appropriate user interface
 * based on the staff's role.
 * This class is part of the 'View' layer in the MVC pattern and interacts with the 'Controller' layer through
 * a Manager instance. The GUI allows staff to perform various actions like reading competitor data from a file,
 * registering and removing competitors, updating competitor information, and generating reports.
 * The GUI is built using Java Swing and organizes its components using a combination of layout managers for
 * a structured and user-friendly interface.
 */
public class StaffGUI {
    private JFrame frame;
    private JTextField nameField, idField;
    private final StaffList staffList;
    private final Manager manager;

    /**
     * Constructor that initializes the GUI and sets up the manager.
     * @param manager The manager that handles business logic and data manipulation.
     */
    public StaffGUI(Manager manager) {
        this.manager = manager;
        staffList = new StaffList();
        String filePath = "src/RunStaff.csv";
        staffList.readFile(filePath);
        initializeGUI();
    }

    /**
     * Initializes the GUI components for staff management. This includes setting up the main frame,
     * input fields for staff name and ID, and buttons for different functionalities based on staff roles.
     * It also sets the window's appearance and behavior.
     */
    private void initializeGUI() {
        frame = new JFrame("Staff Management");
        frame.setLayout(new GridBagLayout());
        frame.setSize(500, 600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int x = screenWidth - frame.getWidth();
        int y = (screenHeight - frame.getHeight()) / 2;

        frame.setLocation(x, y);

       Color backgroundColor = new Color(230, 230, 250);
        Color labelColor = new Color(70, 130, 180); // Steel blue
        Font labelFont = new Font("Arial", Font.BOLD, 12);

        frame.getContentPane().setBackground(backgroundColor);
        // Title
        JLabel titleLabel = new JLabel("Staff Sign In");
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

        // Helper for the StaffGUI testers
        GridBagConstraints welcomeLabelConstraints = new GridBagConstraints();
        welcomeLabelConstraints.gridx = 0;
        welcomeLabelConstraints.gridy = 4;
        welcomeLabelConstraints.gridwidth = 2;
        welcomeLabelConstraints.anchor = GridBagConstraints.CENTER;
        JLabel welcomeLabel = new JLabel("Data Entry:John 1111," +
                "Official:Numan 2222");

        frame.add(welcomeLabel, welcomeLabelConstraints);
        frame.setVisible(true);
        Timer focusTimer = new Timer(100, e -> nameField.requestFocusInWindow());
        focusTimer.setRepeats(false);
        focusTimer.start();
    }

    /**
     * Handles the verification of staff based on the entered name and ID. If the credentials are valid,
     * the GUI is updated to reflect the staff's role and associated functionalities.
     */
    private void handleStaffVerification() {
        String name = nameField.getText();
        int staffId = Integer.parseInt(idField.getText());
        Staff staff = staffList.findStaffById(staffId);

        if (staff != null && staff.getName().getFullName().equals(name)) {
            Role role = staff.getStaffRole();
            updateUIForRole(role);
        } else {
            String message = staff != null ? "Name and ID do not match." : "Staff not found.";
            JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the user interface based on the staff's role. Different components are added to the frame
     * depending on whether the staff is in data entry, an official, part of emergency response, or a referee.
     *
     * @param role The role of the staff which determines the UI components to display.
     */
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

    /**
     * Adds components specific to data entry staff role. This includes buttons for tasks like file reading,
     * competitor searching, score entering, and viewing or sorting competitors.
     */
    private void addDataEntryComponents() {
        JButton btnExitApp = createStyledButton("Close");
        JButton btnReadFile = createStyledButton("Read from File");
        JButton btnSearchCompetitor = createStyledButton("Search Competitor");
        JButton btnEnterScore = createStyledButton("Enter Score");
        JButton btnViewAllCompetitors = createStyledButton("View All Competitors");
        JButton btnBrowseCategories = createStyledButton("Competitors by Category");
        JButton btnSortByAge= createStyledButton("Sort by Age");
        JButton btnSortByFirstName= createStyledButton("Sort by Name");
        JButton btnShowTopScorer = createStyledButton("Show Top Scorer");

        btnExitApp.addActionListener(createExitAppListener());
        btnReadFile.addActionListener(createReadFileListener());
        btnSearchCompetitor.addActionListener(createSearchCompetitorListener());
        btnEnterScore.addActionListener(createEnterScoreListener());
        btnViewAllCompetitors.addActionListener(createViewAllCompetitorsListener());
        btnBrowseCategories.addActionListener(createBrowseCategoriesListener());
        btnSortByAge.addActionListener(createSortByAgeListener());
        btnSortByFirstName.addActionListener(createSortByFirstNameListener());
        btnShowTopScorer.addActionListener(createShowTopScorerListener());


        frame.add(btnExitApp);
        frame.add(btnReadFile);
        frame.add(btnSearchCompetitor);
        frame.add(btnEnterScore);
        frame.add(btnViewAllCompetitors);
        frame.add(btnBrowseCategories);
        frame.add(btnSortByAge);
        frame.add(btnSortByFirstName);
        frame.add(btnShowTopScorer);
    }

    /**
     * Creates a styled button with specified text. The button is customized with specific font, background
     * color, text color, and mouse hover effects.
     *
     * @param text The text to display on the button.
     * @return A JButton styled according to specifications.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.RED);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(23, 162, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });

        return button;
    }


    /**
     * Creates an ActionListener for exiting the application. It prompts the user for confirmation and, upon
     * confirmation, saves data to a file and closes the application.
     *
     * @return An ActionListener that handles the application exit process.
     */
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
                manager.generateFinalReport("src/Competition_report.txt");
                frame.dispose();
            }
        };
    }

    /**
     * Creates an ActionListener for reading a file. It prompts the user to select a file or enter a file path.
     * If a valid file path is provided, the method attempts to read from the file and handle any exceptions that occur.
     *
     * @return An ActionListener that handles file reading operations.
     */
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
                filePath = JOptionPane.showInputDialog(frame, "Enter file path eg. src/Gamers.txt:");
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

    /**
     * Creates an ActionListener for searching a competitor by number. It prompts the user to enter a competitor number,
     * then attempts to find and display the competitor's details.
     *
     * @return An ActionListener that handles searching for a competitor.
     */
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

    /**
     * Creates an ActionListener for entering scores for a competitor. It prompts the user to enter a competitor ID and scores,
     * then updates the competitor's scores accordingly.
     *
     * @return An ActionListener that handles entering and updating competitor scores.
     */
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
                    int maxScore = competitor instanceof Gamer ? 5 : 4;
                    int[] scores = new int[numScores];

                    for (int i = 0; i < numScores; i++) {
                        boolean validScore = false;
                        while (!validScore) {
                            String scoreStr = JOptionPane.showInputDialog(frame, "Enter score " + (i + 1) + " for " +
                                    competitor.getName().getFullName() +"(0-"+maxScore +"):");
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

    /**
     * Creates a JScrollPane containing a non-editable JTextArea displaying the provided text.
     * This method is useful for displaying large text content in a scrollable area.
     *
     * @param text The text to be displayed in the JTextArea.
     * @return A JScrollPane containing the JTextArea with the given text.
     */
    private JScrollPane createScrollPaneForText(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        return scrollPane;
    }

    /**
     * Creates an ActionListener that displays all competitors in different categories. It gathers and shows
     * competitor information in a scrollable pane.
     *
     * @return An ActionListener that handles displaying all competitors.
     */
    private ActionListener createViewAllCompetitorsListener() {
        return e -> {
            String iceSkaters = manager.getCompetitorsTable(Category.ICE_SKATING);
            String gamers = manager.getCompetitorsTable(Category.GAMING);

            JScrollPane scrollPane = createScrollPaneForText(iceSkaters + gamers);
            JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);
        };
    }

    /**
     * Prompts the user to select a category from a predefined list. This method displays a dialog box with
     * options like "ICE SKATING", "GAMING", etc., for the user to choose from.
     *
     * @param frame The JFrame on which to display the dialog box.
     * @return The selected category as a String. Returns null if the dialog is closed without a selection.
     */
    private Category selectCategory(JFrame frame) {
        Category[] categories = Category.values();
        String[] categoryNames = Arrays.stream(categories).map(Enum::name).toArray(String[]::new);

        int categoryChoice = JOptionPane.showOptionDialog(
                frame,
                "Select a category:",
                "Category Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                categoryNames,
                categoryNames[0]);

        if (categoryChoice == JOptionPane.CLOSED_OPTION) {
            return null;
        } else {
            return categories[categoryChoice];
        }
    }


    /**
     * Determines the levels available based on the selected category. For "ICE SKATING", levels such as
     * BEGINNER, INTERMEDIATE, and ADVANCED are returned. For other categories like "GAMING", different
     * levels like NOVICE and EXPERT are returned.
     *
     * @param selectedCategory The category based on which levels are determined.
     * @return An array of Level enumerations applicable to the selected category.
     */
    private Level[] createLevelsBasedOnCategory(Category selectedCategory) {
        if (selectedCategory.equals(Category.ICE_SKATING)) {
            return new Level[]{Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED};
        } else {
            return new Level[]{Level.NOVICE, Level.EXPERT};
        }
    }

    /**
     * Creates an ActionListener that allows browsing through categories. It uses selectCategory to let the
     * user pick a category and then displays competitors in that category.
     *
     * @return An ActionListener for browsing categories.
     */
    private ActionListener createBrowseCategoriesListener() {
        return e -> {
            Category selectedCategory = selectCategory(frame);

            if (selectedCategory != null) {
                String competitors = manager.getCompetitorsTable(selectedCategory);

                JScrollPane scrollPane = createScrollPaneForText(competitors);
                JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors in " + selectedCategory.name(), JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    /**
     * Creates an ActionListener for sorting competitors by age within a selected category and level. The user
     * is first asked to select a category and level, and then competitors are displayed sorted by age.
     *
     * @return An ActionListener for sorting competitors by age.
     */
    private ActionListener createSortByAgeListener() {
        return e -> {
            Category selectedCategory = selectCategory(frame);
            if (selectedCategory != null) {
                Level[] levels = createLevelsBasedOnCategory(selectedCategory);

                Level selectedLevel = (Level) JOptionPane.showInputDialog(
                        frame,
                        "Select the level:",
                        "Level Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        levels,
                        levels[0]);

                if (selectedLevel != null) {
                    ArrayList<Competitor> competitors = manager.sortByAge(selectedCategory, selectedLevel);
                    StringBuilder competitorTable = new StringBuilder();

                    for (Competitor competitor : competitors) {
                        competitorTable.append(competitor.getFullDetails()).append("\n");
                    }
                    JScrollPane scrollPane = createScrollPaneForText(competitorTable.toString());
                    JOptionPane.showMessageDialog(frame, scrollPane, "Competitors Sorted by Age in " + selectedCategory.name(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }

    /**
     * Creates an ActionListener for sorting competitors by first name. It prompts the user to select a category
     * and level, then sorts and displays competitors based on their first names.
     *
     * @return An ActionListener for sorting competitors by their first names.
     */
    private ActionListener createSortByFirstNameListener() {
        return e -> {
            Category selectedCategory = selectCategory(frame);
            if (selectedCategory != null) {
                Level[] levels = createLevelsBasedOnCategory(selectedCategory);

                Level selectedLevel = (Level) JOptionPane.showInputDialog(
                        frame,
                        "Select the level:",
                        "Level Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        levels,
                        levels[0]);

                if (selectedLevel != null) {
                    ArrayList<Competitor> competitors = manager.sortByFirstName(selectedCategory, selectedLevel);
                    StringBuilder competitorTable = new StringBuilder();

                    for (Competitor competitor : competitors) {
                        competitorTable.append(competitor.getFullDetails()).append("\n");
                    }
                    JScrollPane scrollPane = createScrollPaneForText(competitorTable.toString());
                    JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
    }

    /**
     * Creates an ActionListener to show the top scorer in a selected category and level. It uses dialogs to
     * allow the user to choose a category and level, then displays the top scorer's details.
     *
     * @return An ActionListener for displaying the top scorer in a category.
     */
    private ActionListener createShowTopScorerListener() {
        return e -> {
            Category selectedCategory = selectCategory(frame);
            if (selectedCategory != null) {
                Level[] levels = createLevelsBasedOnCategory(selectedCategory);

                Level selectedLevel = (Level) JOptionPane.showInputDialog(
                        frame,
                        "Select the level:",
                        "Level Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        levels,
                        levels[0]);

                if (selectedLevel != null) {
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


    /**
     * Adds components specific to officials to the frame. This includes buttons for registering, removing,
     * and updating competitor information. It uses helper methods to create each component and action listener.
     */
    private void addOfficialsComponents() {
        //Officials can do all the work done by data entry staff
        addDataEntryComponents();

        //Additional work can be done by Officials
        JButton btnRegisterCompetitor = createStyledButton("Register Competitor");
        JButton btnRemoveCompetitor = createStyledButton("Remove Competitor");
        JButton btnUpdateCompetitorInfo = createStyledButton("Update Competitor Info");


        btnRegisterCompetitor.addActionListener(createRegisterCompetitorListener());
        btnRemoveCompetitor.addActionListener(createRemoveCompetitorListener());
        btnUpdateCompetitorInfo.addActionListener(createUpdateCompetitorInfoListener());

        frame.add(btnRegisterCompetitor);
        frame.add(btnRemoveCompetitor);
        frame.add(btnUpdateCompetitorInfo);

    }

    /**
     * Creates an ActionListener for registering a new competitor. It opens a registration form for entering
     * competitor details.
     *
     * @return An ActionListener that opens a registration form for new competitors.
     */
    private ActionListener createRegisterCompetitorListener() {
        return e -> new RegistrationForm(manager);
    }

    /**
     * Creates an ActionListener for removing a competitor. It prompts the user to enter the ID of the
     * competitor to be removed and then proceeds with the removal process.
     *
     * @return An ActionListener that handles the removal of a competitor.
     */
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

    /**
     * Creates an ActionListener for updating competitor information. It prompts the user to enter a competitor ID
     * and the details to be updated, and then updates the competitor's information accordingly.
     *
     * @return An ActionListener that handles updating competitor information.
     */
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
                                case "Name"    -> competitor.setName(new Name(newValue));
                                case "Email"   -> competitor.setEmail(newValue);
                                case "Age"     -> competitor.setAge(Integer.parseInt(newValue));
                                case "Gender"  -> competitor.setGender(newValue);
                                case "Country" -> competitor.setCountry(newValue);
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
        //There is not much information about this.So it has not been implemented.
    }

    private void addRefereeComponents() {
        ///There is not much information about this.So it has not been implemented.
    }


}
