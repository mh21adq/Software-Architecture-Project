package View;
import Model.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;

/**
 * The CompetitorGUI class creates the graphical user interface for the competitor management system.
 * It provides a window with options to register a new competitor, search for existing competitors,
 * and generate reports. This class is part of the 'View' in the MVC pattern and interacts with
 * the 'Controller' through a Manager instance.
 * The GUI is composed of a JFrame to which a panel containing buttons is added. These buttons
 * are associated with various functionalities of the application, each handled by separate private
 * methods that instantiate different parts of the GUI as needed.
 */
public class CompetitorGUI {
    private JFrame frame;
    private final Manager manager;

    /**
     * Constructor that initializes the GUI and sets up the manager.
     * @param manager The manager that handles business logic and data manipulation.
     */
    public CompetitorGUI(Manager manager) {
        initializeGUI();
        this.manager=manager;
    }

    /**
     * Initializes the main GUI components, sets up the layout, and adds action listeners
     * to the buttons for registering competitors, searching, and report generation.
     */
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
        btnGenerateReport.addActionListener(e -> openPrintReportDialog());
        // Display the frame
        frame.setVisible(true);
    }

    /**
     * Creates a button with specified text, color, and font, and applies default styling.
     * @param text  The text to be displayed on the button.
     * @param color The background color of the button.
     * @param font  The font used for the button's text.
     * @return A styled JButton with the specified properties.
     */
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

    /**
     * Opens the registration form dialog to register a new competitor.
     */
    private void openRegistrationForm() {
        new RegistrationForm(manager);
    }

    /**
     * Opens a dialog to search for a competitor by ID, then displays the competitor's details
     * or an appropriate message if the competitor is not found or the information is incomplete.
     */
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
                    JOptionPane.showMessageDialog(frame, competitor.getShortDetails(), "Competitor Details", JOptionPane.INFORMATION_MESSAGE);
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


    /**
     * Displays a dialog allowing the user to select a category, then generates and displays a report
     * for all competitors in that category, including summary statistics.
     */
    private void openPrintReportDialog() {
        String[] categories = {"ICE SKATING", "GAMING"};
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
            String competitorsTable = manager.getCompetitorsTable(selectedCategory);
            String summaryStatistics=manager.getSummaryStatistics(selectedCategory);
            String combinedText = competitorsTable + "\n\n" + summaryStatistics;
            JTextArea textArea = new JTextArea(combinedText);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Competitors in " + selectedCategory, JOptionPane.INFORMATION_MESSAGE);
        }
    }



}

