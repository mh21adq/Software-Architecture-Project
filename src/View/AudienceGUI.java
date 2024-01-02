package View;
import Controller.Manager;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AudienceGUI {
    private JFrame frame;
    private JSplitPane splitPane;
    private JTextArea gamersTextArea;
    private JTextArea iceSkatersTextArea;
    private JButton refreshButton;
    private Manager manager;

    public AudienceGUI(Manager manager) {
        this.manager = manager;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Live Competition Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 600);

        // Font and Color Setup
        Font titleFont = new Font("Monospaced", Font.BOLD, 20);
        Font textFont = new Font("Monospaced", Font.PLAIN, 16);
        Color backgroundColor = new Color(25, 25, 25); // Dark background
        Color textColor = new Color(255, 215, 0); // Gold text for contrast

        // Gamers Section
        JLabel gamersLabel = new JLabel("Gamers", SwingConstants.CENTER);
        gamersLabel.setFont(titleFont);
        gamersLabel.setForeground(textColor);
        gamersTextArea = new JTextArea();
        gamersTextArea.setFont(textFont);
        gamersTextArea.setForeground(textColor);
        gamersTextArea.setBackground(backgroundColor);
        JPanel gamersPanel = new JPanel(new BorderLayout());
        gamersPanel.setBackground(backgroundColor);
        gamersPanel.add(gamersLabel, BorderLayout.NORTH);
        gamersPanel.add(new JScrollPane(gamersTextArea), BorderLayout.CENTER);

        // Ice Skaters Section
        JLabel iceSkatersLabel = new JLabel("Ice Skaters", SwingConstants.CENTER);
        iceSkatersLabel.setFont(titleFont);
        iceSkatersLabel.setForeground(textColor);
        iceSkatersTextArea = new JTextArea();
        iceSkatersTextArea.setFont(textFont);
        iceSkatersTextArea.setForeground(textColor);
        iceSkatersTextArea.setBackground(backgroundColor);
        JPanel iceSkatersPanel = new JPanel(new BorderLayout());
        iceSkatersPanel.setBackground(backgroundColor);
        iceSkatersPanel.add(iceSkatersLabel, BorderLayout.NORTH);
        iceSkatersPanel.add(new JScrollPane(iceSkatersTextArea), BorderLayout.CENTER);

        // Split Pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamersPanel, iceSkatersPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setBackground(backgroundColor);

        // Refresh Button
        refreshButton = new JButton("Refresh Results");
        refreshButton.setFont(titleFont);
        refreshButton.setForeground(Color.RED);
        refreshButton.setBackground(new Color(30, 30, 30)); //
        refreshButton.addActionListener(e -> refreshResults());

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }


    private void refreshResults() {
        // Clear the previous results from the text areas
        gamersTextArea.setText("Gamers Results:\n");
        iceSkatersTextArea.setText("Ice Skaters Results:\n");

        // Define specific levels for gamers and ice skaters
        Level[] gamerLevels = {Level.NOVICE, Level.EXPERT};
        Level[] iceSkaterLevels = {Level.BEGINNER, Level.INTERMEDIATE, Level.ADVANCED};

        // Process Gamers
        processCompetitors(gamersTextArea, "Gaming", gamerLevels);

        // Process Ice Skaters
        processCompetitors(iceSkatersTextArea, "Ice Skating", iceSkaterLevels);
    }

    private void processCompetitors(JTextArea textArea, String category, Level[] levels) {
        StringBuilder results = new StringBuilder();

        for (Level level : levels) {
            ArrayList<Competitor> competitors = manager.sortCompetitorsByScore(category, level);
            results.append(level.name()).append(":\n");
            int ranking=1;
            for (Competitor competitor : competitors) {
                String firstName = competitor.getName().getFirstName();
                String initials = competitor.getName().getInitials();
                results.append(ranking++).append(". ").append(firstName).append(" (").append(initials).append(") ")
                        .append(String.format("%.2f", competitor.getOverallScore())).append("\n");
            }
        }
        textArea.append(results.toString());
    }


}
