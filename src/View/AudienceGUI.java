package View;
import Controller.Manager;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private void initializeGUI() {frame = new JFrame("Live Competition Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(550, 800);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(0, 0);

        // Create the text areas for gamers and ice skaters
        gamersTextArea = new JTextArea();
        iceSkatersTextArea = new JTextArea();

        // Create labels for the titles
        JLabel gamersLabel = new JLabel("Gamers", SwingConstants.CENTER);
        JLabel iceSkatersLabel = new JLabel("Ice Skaters", SwingConstants.CENTER);

        // Create panels to include both the labels and text areas
        JPanel gamersPanel = new JPanel(new BorderLayout());
        JPanel iceSkatersPanel = new JPanel(new BorderLayout());

        // Add the labels and text areas to the respective panels
        gamersPanel.add(gamersLabel, BorderLayout.NORTH);
        gamersPanel.add(new JScrollPane(gamersTextArea), BorderLayout.CENTER);

        iceSkatersPanel.add(iceSkatersLabel, BorderLayout.NORTH);
        iceSkatersPanel.add(new JScrollPane(iceSkatersTextArea), BorderLayout.CENTER);

        // Create the split pane with the newly created panels
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamersPanel, iceSkatersPanel);
        splitPane.setResizeWeight(0.5); // Adjust the initial divider position

        // Create the refresh button and its action listener
        refreshButton = new JButton("Refresh Results");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshResults();
            }
        });

        // Add the split pane and refresh button to the frame
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void refreshResults() {
        // Clear the previous results from the text areas
        gamersTextArea.setText("Gamers Results:\n");
        iceSkatersTextArea.setText("Ice Skaters Results:\n");

        // Iterate over all levels defined in the Level enum
        for (Level level : Level.values()) {
            ArrayList<Competitor> gamers = manager.searchCompetitorsByLevel("Gaming", level);
            ArrayList<Competitor> iceSkaters = manager.searchCompetitorsByLevel("Ice Skating", level);

            StringBuilder gamersResults = new StringBuilder();
            StringBuilder iceSkatersResults = new StringBuilder();

            // Append the level and the competitors in that level
            gamersResults.append(level.name()).append(":\n");
            iceSkatersResults.append(level.name()).append(":\n");

            int competitorNumber = 1;
            for (Competitor gamer : gamers) {
                String firstName = gamer.getName().getFirstName();
                String initials = gamer.getName().getInitials();
                gamersResults.append(competitorNumber).append(". ").append(firstName).append("(").append(initials).append(")").append("\t");
                gamersResults.append(Arrays.toString(gamer.getScoresArray()).replace("[", "").replace("]", "")).append("\t");
                gamersResults.append(String.format("%.2f", gamer.getOverallScore())).append("\n");
                competitorNumber++;
            }

            competitorNumber = 1; // Reset for ice skaters
            for (Competitor iceSkater : iceSkaters) {
                String firstName = iceSkater.getName().getFirstName();
                String initials = iceSkater.getName().getInitials();
                iceSkatersResults.append(competitorNumber).append(". ").append(firstName).append("(").append(initials).append(")").append("\t");
                iceSkatersResults.append(Arrays.toString(iceSkater.getScoresArray()).replace("[", "").replace("]", "")).append("\t");
                iceSkatersResults.append(String.format("%.2f", iceSkater.getOverallScore())).append("\n");
                competitorNumber++;
            }

            // Append the results to the text areas
            gamersTextArea.append(gamersResults.toString());
            iceSkatersTextArea.append(iceSkatersResults.toString());
        }
    }


}
