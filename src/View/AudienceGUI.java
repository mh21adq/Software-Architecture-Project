package View;
import Controller.Manager;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The AudienceGUI class creates and displays the graphical user interface for audience members
 * to view live competition results. It provides separate sections for different categories of competitors.
 */
public class AudienceGUI{
    private JTextArea gamersTextArea;
    private JTextArea iceSkatersTextArea;
    private final Manager manager;

    /**
     * Constructs an AudienceGUI object with the specified Manager.
     *
     * @param manager The manager used to handle data interactions and updates.
     */
    public AudienceGUI(Manager manager) {
        this.manager = manager;
        initializeGUI();
    }

    /**
     * Initializes the user interface components and layouts for the audience view.
     * This includes setting up sections for gamers and ice skaters, and providing a refresh button.
     */
    private void initializeGUI() {
        JFrame frame;
        frame = new JFrame("Live Competition Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(620, 600);

        // Font and Color Setup
        Font titleFont = new Font("Monospaced", Font.BOLD, 20);
        Font textFont = new Font("Monospaced", Font.PLAIN, 16);
        Color backgroundColor = new Color(25, 25, 25); // Dark background
        Color textColor = new Color(255, 215, 0); // Gold text for contrast

        // Gamers Section
        JLabel gamersLabel = createLabel("Gamers", titleFont, textColor);
        gamersTextArea = createTextArea(textFont, textColor, backgroundColor);
        JPanel gamersPanel = new JPanel(new BorderLayout());
        gamersPanel.setBackground(backgroundColor);
        gamersPanel.add(gamersLabel, BorderLayout.NORTH);
        gamersPanel.add(new JScrollPane(gamersTextArea), BorderLayout.CENTER);
        // Ice Skaters Section
        JLabel iceSkatersLabel = createLabel("Ice Skaters", titleFont, textColor);
        iceSkatersTextArea = createTextArea(textFont, textColor, backgroundColor);
        JPanel iceSkatersPanel = new JPanel(new BorderLayout());
        iceSkatersPanel.setBackground(backgroundColor);
        iceSkatersPanel.add(iceSkatersLabel, BorderLayout.NORTH);
        iceSkatersPanel.add(new JScrollPane(iceSkatersTextArea), BorderLayout.CENTER);

        iceSkatersPanel.add(new JScrollPane(iceSkatersTextArea), BorderLayout.CENTER);

        // Split Pane
        JSplitPane splitPane;
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamersPanel, iceSkatersPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setBackground(backgroundColor);
        // Refresh Button
        JButton refreshButton;
        refreshButton = new JButton("Refresh Results");
        refreshButton.setFont(titleFont);
        refreshButton.setForeground(Color.RED);
        refreshButton.setBackground(new Color(30, 30, 30)); //
        refreshButton.addActionListener(e -> refreshResults());

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Creates and returns a JLabel with specified text, font, and text color.
     *
     * @param text The text to be displayed on the label.
     * @param font The font to be applied to the label text.
     * @param textColor The color of the label text.
     * @return A JLabel with the specified properties.
     */
    private JLabel createLabel(String text, Font font, Color textColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(textColor);
        return label;
    }

    /**
     * Creates and returns a JTextArea with specified font, text color, and background color.
     *
     * @param font The font to be applied to the text area.
     * @param textColor The color of the text.
     * @param backgroundColor The background color of the text area.
     * @return A JTextArea with the specified properties.
     */
    private JTextArea createTextArea(Font font, Color textColor, Color backgroundColor) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setForeground(textColor);
        textArea.setBackground(backgroundColor);
        return textArea;
    }


    /**
     * Refreshes the results displayed in the text areas, updating them with the latest competition results.
     * It clears the previous results and fetches updated data for each category and level.
     */
    private void refreshResults() {
        // Clear the previous results from the text areas
        gamersTextArea.setText("Gamers Results:\n");
        iceSkatersTextArea.setText("Ice Skaters Results:\n");

        // Define specific levels for gamers and ice skaters
        Level[] gamerLevels = {Level.NOVICE, Level.EXPERT};
        Level[] iceSkaterLevels = {Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED};

        // Process Gamers
        processCompetitors(gamersTextArea, Category.GAMING, gamerLevels);

        // Process Ice Skaters
        processCompetitors(iceSkatersTextArea, Category.ICE_SKATING, iceSkaterLevels);
    }

    /**
     * Processes and displays a sorted list of competitors by their scores within a specified category and levels.
     * This method retrieves competitors from the manager, sorts them by their overall score for each level,
     * and appends the results to the provided JTextArea.
     * Each competitor's first name and initials, along with their overall score formatted to two decimal places,
     * are displayed in a ranked list for each level.
     *
     * @param textArea The JTextArea component where the sorted competitors' information will be displayed.
     * @param category The category of the competitors to be processed (e.g., ICE SKATING or GAMING).
     * @param levels   An array of Level enums representing the competition levels to include in the processing.
     */
    private void processCompetitors(JTextArea textArea, Category category, Level[] levels) {
        StringBuilder results = new StringBuilder();

        for (Level level : levels) {
            ArrayList<Competitor> competitors = manager.sortCompetitorsByScore(category, level);
            results.append("Level: ").append(level.name()).append("\n");

            int ranking = 1;
            for (Competitor competitor : competitors) {
                String firstName = competitor.getName().getFirstName();
                String initials = competitor.getName().getInitials();
                results.append(String.format("%d.[%d] %s (%s) - %.2f\n",
                        ranking++, competitor.getCompetitorNumber(),
                        firstName, initials,
                        competitor.getOverallScore()));
            }
            results.append("\n");
        }
        textArea.append(results.toString());
    }



}
