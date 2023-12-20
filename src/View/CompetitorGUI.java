package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CompetitorGUI {

    private JFrame frame;
    private JButton registerButton;

    public CompetitorGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Competitor Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout()); // Layout for the main window
        frame.setSize(300, 200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = frame.getSize().width;
        int height = frame.getSize().height;
        frame.setLocation(0, (screenSize.height - height) / 2);

        // Register button
        registerButton = new JButton("Register Competitor");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });

        frame.add(registerButton);

        frame.setVisible(true);
    }

    private void openRegistrationForm() {
        // Logic to open the registration form
        new RegistrationGUI();
    }

    public static void main(String[] args) {
        new CompetitorGUI();
    }
}
