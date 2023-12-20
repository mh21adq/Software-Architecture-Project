package View;

import Controller.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffGUI {
    private JFrame frame;
    private JButton btnSearchCompetitor, btnManageCompetitor, btnRegisterCompetitor, btnRemoveCompetitor;

    public StaffGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Staff Competitor Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(800, 600); // Set the window size

        btnSearchCompetitor = new JButton("Search Competitor");

        btnManageCompetitor = new JButton("Manage Competitor");
        btnRegisterCompetitor = new JButton("Register Competitor");
        btnRemoveCompetitor = new JButton("Remove Competitor");

        frame.add(btnSearchCompetitor);
        frame.add(btnManageCompetitor);
        frame.add(btnRegisterCompetitor);
        frame.add(btnRemoveCompetitor);

        // Add action listeners to buttons
        btnSearchCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchCompetitorDialog();
            }
        });

        btnManageCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageCompetitorDialog();
            }
        });

        btnRegisterCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterCompetitorDialog();
            }
        });

        btnRemoveCompetitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveCompetitorDialog();
            }
        });

        frame.setVisible(true);
    }

    private void openSearchCompetitorDialog() {
        String competitorIdStr = JOptionPane.showInputDialog(frame, "Enter Competitor ID:", "Search Competitor", JOptionPane.QUESTION_MESSAGE);

        if (competitorIdStr != null && !competitorIdStr.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(competitorIdStr);
                Manager manager=new Manager();
                manager.searchCompetitor(competitorId);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void openManageCompetitorDialog() {
        // Implement the logic to open the Manage Competitor dialog
    }

    private void openRegisterCompetitorDialog() {
        // Implement the logic to open the Register Competitor dialog
    }

    private void openRemoveCompetitorDialog() {
        // Implement the logic to open the Remove Competitor dialog
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StaffGUI::new);
    }
}
