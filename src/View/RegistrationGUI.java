package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.Manager;
import Model.*;
public class RegistrationGUI {
    private JFrame frame;
    private JTextField nameField, emailField, ageField,countryField;
    private JComboBox<String> categoryComboBox, levelComboBox;
    private JRadioButton maleButton, femaleButton;
    private JButton registerButton;

    public RegistrationGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Competitor Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 2)); // 2 columns layout

        // Name
        frame.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        frame.add(nameField);

        // Email
        frame.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        frame.add(emailField);

        // Age
        frame.add(new JLabel("Age:"));
        ageField = new JTextField(20);
        frame.add(ageField);
        //country
        frame.add(new JLabel("Country:"));
        countryField = new JTextField(20);
        frame.add(countryField);

        // Gender
        frame.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        frame.add(genderPanel);

        // Category
        frame.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(new String[]{"Gaming", "Ice Skating"}); // Example categories
        frame.add(categoryComboBox);

        // Level
        frame.add(new JLabel("Level:"));
        levelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced", "Professional"}); // Example levels
        frame.add(levelComboBox);

        // Register Button
        registerButton = new JButton("Register");
        frame.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        // Adjusting the window
        frame.pack();
        frame.setVisible(true);
    }

    private void handleRegistration() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText());
            String country=countryField.getText();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
            String category = (String) categoryComboBox.getSelectedItem();
            String level = (String) levelComboBox.getSelectedItem();

            // Assuming Level is an enum and matches the string options in the combo box
            Level levelEnum = Level.valueOf(level.toUpperCase());

            // Creating a competitor based on the category
            Competitor competitor;
            if ("Gaming".equals(category)) {
                competitor = new Gamer(new Name(name), email, age, gender, country, levelEnum);
            } else if ("Ice Skating".equals(category)) {
                competitor = new IceSkater(new Name(name), email, age, gender, country, levelEnum);
            } else {
                // Handle other categories if exist
                competitor = null;
            }

            if (competitor != null) {
                Manager manager=new Manager();
                manager.addCompetitor(competitor);

                JOptionPane.showMessageDialog(frame, "Registration Successful for " + competitor.getName());
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid category.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid age format. Please enter a numeric age.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Invalid level selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationGUI::new);
    }
}
