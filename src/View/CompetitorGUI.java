package View;
import Controller.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompetitorGUI {
    private JFrame frame;
    private JPanel panelViewScores;
    private JPanel panelCompetitorTable;
    private JPanel panelCompetitorDetails;
    private Manager manager; // Assuming Manager class handles all operations

    public CompetitorGUI(Manager manager) {
        this.manager = manager;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Competitor Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));

        panelViewScores = new JPanel();
        setupViewScoresPanel();
        frame.add(panelViewScores);

        panelCompetitorTable = new JPanel();
        setupCompetitorTablePanel();
        frame.add(panelCompetitorTable);

        panelCompetitorDetails = new JPanel();
        setupCompetitorDetailsPanel();
        frame.add(panelCompetitorDetails);

        frame.pack();
        frame.setVisible(true);
    }

    private void setupViewScoresPanel() {
        panelViewScores.setLayout(new BoxLayout(panelViewScores, BoxLayout.Y_AXIS));
        panelViewScores.add(new JLabel("View and Alter Scores"));
        // Add more components and functionalities here
    }

    private void setupCompetitorTablePanel() {
        panelCompetitorTable.setLayout(new BoxLayout(panelCompetitorTable, BoxLayout.Y_AXIS));
        panelCompetitorTable.add(new JLabel("Competitor Table"));
        // Add table and other components here
    }

    private void setupCompetitorDetailsPanel() {
        panelCompetitorDetails.setLayout(new BoxLayout(panelCompetitorDetails, BoxLayout.Y_AXIS));
        panelCompetitorDetails.add(new JLabel("Competitor Details"));
        // Add components for competitor details
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeApplication();
            }
        });
        panelCompetitorDetails.add(closeButton);
    }

    private void closeApplication() {
        // Write competitor report to a file
        // Close the program
        frame.dispose();
    }

    // Main method to start the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompetitorGUI(new Manager())); // Replace with actual Manager object
    }
}
