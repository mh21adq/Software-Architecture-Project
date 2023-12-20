package View;

import javax.swing.*;
import java.awt.*;

public class ScoreboardGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScoreboardGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Competition Scoreboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 5)); // rows, cols, hgap, vgap
        panel.setBackground(Color.BLUE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sample data
        String[][] competitors = {
                {"UKR", "1301 POVKH Olesya", "10.49"},
                {"ECU", "613 TENORIO Angela", "10.62"},
                // ... add more competitors
        };

        for (String[] competitor : competitors) {
            JLabel countryLabel = new JLabel(competitor[0], SwingConstants.CENTER);
            JLabel nameLabel = new JLabel(competitor[1]);
            JLabel timeLabel = new JLabel(competitor[2], SwingConstants.CENTER);

            countryLabel.setForeground(Color.WHITE);
            nameLabel.setForeground(Color.WHITE);
            timeLabel.setForeground(Color.WHITE);

            countryLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

            panel.add(countryLabel);
            panel.add(nameLabel);
            panel.add(timeLabel);
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
