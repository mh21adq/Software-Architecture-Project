package View;

import Model.Competitor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDetails extends JPanel {
    private JTable userTable;
    private String[] userTableColumn = {"Competitor Number", "Name", "Email", "Age", "Gender", "Country", "Category", "Level", "Overall Score"};

    private JButton backButton;

    public UserDetails() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JToolBar toolBar = new JToolBar();
        userTable = new JTable();
        JScrollPane userTableScroll = new JScrollPane(userTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        backButton = new JButton("Go Back");
        add(toolBar);
        toolBar.add(backButton);
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        add(userTableScroll);
    }

    public void setUsers(List<Competitor> competitors) {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setColumnIdentifiers(userTableColumn);

        for (Competitor competitor : competitors) {
            Object[] rowData = {
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getEmail(),
                    competitor.getAge(),
                    competitor.getGender(),
                    competitor.getCountry(),
                    competitor.getCategory(),
                    competitor.getLevel().toString(),
                    competitor.getOverallScore()
            };
            model.addRow(rowData);
        }
    }

    public void backButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }
}
