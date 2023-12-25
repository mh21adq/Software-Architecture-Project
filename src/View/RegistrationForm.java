package View;

import javax.swing.*;
import java.awt.*;
import Controller.Manager;
import Model.*;

public class RegistrationForm {
    private JFrame frame;
    private JTextField nameField, emailField, ageField, countryField;
    private JComboBox<String> categoryComboBox, levelComboBox;
    private JRadioButton maleButton, femaleButton;
    private JButton registerButton;
    private Manager manager;

    public RegistrationForm(Manager manager) {
        this.manager = manager;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Competitor Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Color backgroundColor = new Color(230, 230, 250); // Light lavender
        Color labelColor = new Color(70, 130, 180); // Steel blue
        Font labelFont = new Font("Arial", Font.BOLD, 12);

        frame.getContentPane().setBackground(backgroundColor);

        // Title
        JLabel titleLabel = new JLabel("Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(labelColor);
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Name
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(labelColor);
        nameLabel.setFont(labelFont);
        frame.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        frame.add(nameField, gbc);

        // Email
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(labelColor);
        emailLabel.setFont(labelFont);
        frame.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        frame.add(emailField, gbc);

        // Age
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(labelColor);
        ageLabel.setFont(labelFont);
        frame.add(ageLabel, gbc);

        gbc.gridx = 1;
        ageField = new JTextField(20);
        frame.add(ageField, gbc);

        // Country
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setForeground(labelColor);
        countryLabel.setFont(labelFont);
        frame.add(countryLabel, gbc);

        gbc.gridx = 1;
        countryField = new JTextField(20);
        frame.add(countryField, gbc);

        // Gender
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(labelColor);
        genderLabel.setFont(labelFont);
        frame.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.setBackground(backgroundColor); // Set background color
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        frame.add(genderPanel, gbc);

        // Category
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(labelColor);
        categoryLabel.setFont(labelFont);
        frame.add(categoryLabel, gbc);

        categoryComboBox = new JComboBox<>(new String[]{"Gaming", "Ice Skating"});
        gbc.gridx = 1;
        frame.add(categoryComboBox, gbc);

        // Level
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setForeground(labelColor);
        levelLabel.setFont(labelFont);
        frame.add(levelLabel, gbc);

        levelComboBox = new JComboBox<>();
        updateLevelOptions((String) categoryComboBox.getSelectedItem());
        gbc.gridx = 1;
        frame.add(levelComboBox, gbc);

        // Register Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.setForeground(Color.BLUE);
        registerButton.setBackground(new Color(100, 149, 237)); // Cornflower blue
        frame.add(registerButton, gbc);
        registerButton.addActionListener(e -> handleRegistration());

        // Add action listener to categoryComboBox
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            updateLevelOptions(selectedCategory);
        });

        frame.pack();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void updateLevelOptions(String category) {
        levelComboBox.removeAllItems();
        if ("Gaming".equals(category)) {
            levelComboBox.addItem("NOVICE");
            levelComboBox.addItem("EXPERT");
        } else if ("Ice Skating".equals(category)) {
            levelComboBox.addItem("BEGINNER");
            levelComboBox.addItem("INTERMEDIATE");
            levelComboBox.addItem("ADVANCED");
        }
    }

    private void handleRegistration() {
        try {
            String name = nameField.getText();
            Name competitorName = new Name(name); // Can throw IllegalArgumentException

            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText()); // Can throw NumberFormatException
            String country = countryField.getText();

            String gender = null;
            if (maleButton.isSelected()) {
                gender = "Male";
            } else if (femaleButton.isSelected()) {
                gender = "Female";
            }
            if (gender == null) {
                JOptionPane.showMessageDialog(frame, "Please select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String category = (String) categoryComboBox.getSelectedItem();
            Level levelEnum = Level.valueOf(((String) levelComboBox.getSelectedItem()).toUpperCase());

            Competitor competitor = null;
            if ("Gaming".equals(category)) {
                competitor = new Gamer(competitorName, email, age, gender, country, levelEnum);
            } else { // "Ice Skating"
                competitor = new IceSkater(competitorName, email, age, gender, country, levelEnum);
            }

            if (manager.addCompetitor(competitor)) {
                JOptionPane.showMessageDialog(frame, "Registration Successful for " + competitor.getName().getFullName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Competitor already exists in this category.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid age format. Please enter a numeric age.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
