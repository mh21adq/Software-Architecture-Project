package View;
import Controller.Manager;
import Model.Competitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        frame.setSize(800, 600);

        gamersTextArea = new JTextArea();
        iceSkatersTextArea = new JTextArea();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(gamersTextArea), new JScrollPane(iceSkatersTextArea));
        splitPane.setResizeWeight(0.5); // Adjust the initial divider position

        refreshButton = new JButton("Refresh Results");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshResults();
            }
        });

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refreshResults() {
        ArrayList<Competitor> gamers = manager.getCompetitorsByCategory("Gaming");
        ArrayList<Competitor> iceSkaters = manager.getCompetitorsByCategory("Ice Skating");

        StringBuilder gamersResults = new StringBuilder("Gamers Results:\n");
        StringBuilder iceSkatersResults = new StringBuilder("Ice Skaters Results:\n");

        // Append gamer results
        for (Competitor gamer : gamers) {
            gamersResults.append(gamer.getName()).append("\t\t\t")
                    .append(gamer.getOverallScore()).append("\n");
        }

        // Append ice skater results
        for (Competitor iceSkater : iceSkaters) {
            iceSkatersResults.append(iceSkater.getName()).append("\t\t\t")
                    .append(iceSkater.getScores()).append("\t\t")
                    .append(iceSkater.getOverallScore()).append("\n");
        }

        gamersTextArea.setText(gamersResults.toString());
        iceSkatersTextArea.setText(iceSkatersResults.toString());
    }
}
