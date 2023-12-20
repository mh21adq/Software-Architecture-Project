package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationGUI {
    private JFrame frame;
    private JTextField nameField, emailField, ageField;
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
        // Logic to handle registration
        // Fetch data from fields and perform registration
        String name = nameField.getText();
        String email = emailField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = maleButton.isSelected() ? "Male" : "Female";
        String category = (String) categoryComboBox.getSelectedItem();
        String level = (String) levelComboBox.getSelectedItem();

        // TODO: Add your registration logic here

        JOptionPane.showMessageDialog(frame, "Registration Successful!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationGUI::new);
    }
}
