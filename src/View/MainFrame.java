package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel staffGUI;     // Assuming StaffGUI is a JPanel or similar
    private JPanel competitorGUI; // Assuming CompetitorGUI is a JPanel or similar
    private JPanel audienceGUI;  // Assuming AudienceGUI is a JPanel or similar

    public MainFrame() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize your GUI components
        staffGUI = new JPanel(); // Initialize your actual StaffGUI
        competitorGUI = new JPanel(); // Initialize your actual CompetitorGUI
        audienceGUI = new JPanel(); // Initialize your actual AudienceGUI

        // Add StaffGUI to the top half of the BorderLayout
        staffGUI.setPreferredSize(new Dimension(getWidth(), getHeight() / 2));
        add(staffGUI, BorderLayout.NORTH);

        // Create a split pane for CompetitorGUI and AudienceGUI
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                competitorGUI,
                audienceGUI
        );
        splitPane.setResizeWeight(0.5); // Adjusts the divider position

        // Add the split pane to the center of the BorderLayout
        add(splitPane, BorderLayout.CENTER);

        // Set the size of the main frame and make it visible
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
