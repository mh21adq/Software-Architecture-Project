package View;

import Controller.Manager;

import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame {

    // Card layout for switching view
    private CardLayout cardLayout;

    public MainFrame() {
        super("MVC Pattern Example ");
        cardLayout = new CardLayout();
        Form form = new Form();
        //UserDetails userDetails = new UserDetails();

        setLayout(cardLayout);

        add(form, "form");
        //add();

        form.viewUsers(e -> cardLayout.show(MainFrame.this.getContentPane(), "user details"));
        //userDetails.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "form"));

        form.submitUsers(e -> {
            form.createUser(); // This will call the createUser method and print the data
        });

        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}

