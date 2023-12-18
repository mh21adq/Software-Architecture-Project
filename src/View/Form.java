package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Model.*;

public class Form extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField ageField;
    private ButtonGroup genderGroup;   // Group for gender radio buttons
    private JRadioButton maleButton, femaleButton;  // Gender radio buttons
    private JTextField countryField;
    private JComboBox<String> userTypeComboBox;  // ComboBox for User Type
    private JComboBox<Level> levelComboBox;  // ComboBox for Level

    private JButton addButton;
    private JButton viewButton;

    public Form() {
        JLabel nameLabel = new JLabel("Full Name: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel ageLabel = new JLabel("Age: ");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel countryLabel = new JLabel("Country: ");
        JLabel userTypeLabel = new JLabel("User Type: ");
        JLabel levelLabel = new JLabel("Level: ");

        // Text fields
        nameField = new JTextField(25);
        emailField = new JTextField(25);
        ageField = new JTextField(25);
        countryField = new JTextField(25);

        // User Type ComboBox
        userTypeComboBox = new JComboBox<>(new String[]{"Gamer", "Ice Skater"});

        // Gender Radio Buttons
        genderGroup = new ButtonGroup();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        // Level ComboBox
        levelComboBox = new JComboBox<>(Level.values());  // Populate with Level enum values

        // Buttons
        addButton = new JButton("Add User");
        addButton.setPreferredSize(new Dimension(278, 40));
        viewButton = new JButton("View All Users");
        viewButton.setPreferredSize(new Dimension(278, 40));

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        Insets fieldsInset = new Insets(0, 0, 10, 0);
        Insets buttonInset = new Insets(20, 0, 0, 0);

        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        // Add components to the layout
        // Name
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(nameField, gridBagConstraints);

        // Email
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(emailLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(emailField, gridBagConstraints);

        // Age
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(ageLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(ageField, gridBagConstraints);

        // Gender
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(genderLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(maleButton, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        add(femaleButton, gridBagConstraints);

        // Country
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(countryLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(countryField, gridBagConstraints);

        // User Type
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        add(userTypeLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        add(userTypeComboBox, gridBagConstraints);

        // Level
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        add(levelLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        add(levelComboBox, gridBagConstraints);

        // Buttons
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = buttonInset;
        add(addButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = buttonInset;
        add(viewButton, gridBagConstraints);
    }

    // Getters
    public String getFullName() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public int getAge() {
        try {
            return Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            return 0; // Handle invalid input gracefully
        }
    }

    public String getGender() {
        return genderGroup.getSelection().getActionCommand();
    }

    public String getCountry() {
        return countryField.getText();
    }

    public Level getLevel() {
        return (Level) levelComboBox.getSelectedItem();
    }

    public void submitUsers(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }
    public String getUserType() {
        return (String) userTypeComboBox.getSelectedItem();
    }
    public void viewUsers(ActionListener actionListener) {
        viewButton.addActionListener(actionListener);
    }

    // Method to reset fields
    public void reset(boolean bln) {
        if (bln) {
            nameField.setText("");
            emailField.setText("");
            ageField.setText("");
            genderGroup.clearSelection();
            countryField.setText("");
            userTypeComboBox.setSelectedIndex(0);
            levelComboBox.setSelectedIndex(0);
        }
    }


    public Competitor createUser() {
        String fullName = nameField.getText();
        String email = emailField.getText();
        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            // Handle invalid age input
            age = 0; // or some default value or throw an exception
        }
        String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
        String country = countryField.getText();
        String userType = (String) userTypeComboBox.getSelectedItem();
        Level level = Level.valueOf(levelComboBox.getSelectedItem().toString().toUpperCase());

        // Create a Name object from the full name
        Name name = createNameFromFullName(fullName);

        Competitor competitor = null;
        if ("GAMER".equalsIgnoreCase(userType)) {
            competitor = new Gamer(name, email, age, gender, country, level);
            competitor.setScores(new int[]{0, 0, 0, 0, 0});
        } else if ("ICE SKATER".equalsIgnoreCase(userType)) {
            competitor = new IceSkater(name, email, age, gender, country, level);
            competitor.setScores(new int[]{0, 0, 0, 0});
        }

        // Optional: Print competitor number, handle it outside if necessary
        // System.out.println(competitor != null ? competitor.getCompetitorNumber() : "No competitor created");

        return competitor;
    }

    private Name createNameFromFullName(String fullName) {
        String[] nameParts = fullName.trim().split("\\s+");
        switch (nameParts.length) {
            case 1:
                return new Name(nameParts[0]);
            case 2:
                return new Name(nameParts[0], nameParts[1]);
            case 3:
            default:
                return new Name(nameParts[0], nameParts[1], nameParts[2]);
        }
    }




}
