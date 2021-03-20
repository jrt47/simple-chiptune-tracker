package ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMenu extends JPanel {
    private MainMenu mainMenu;

    private JTextField textField;
    private JButton createButton;
    private JButton backButton;

    public NewMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.CENTER, MainMenu.SPACING, MainMenu.SPACING));

        JLabel label = new JLabel("Track Title:");
        textField = new JTextField(18);
        createButton = new JButton("Create");
        backButton = new JButton("Back");

        label.setFont(MainMenu.FONT);
        MainMenu.formatTextBox(textField);
        MainMenu.formatSubButton(createButton);
        MainMenu.formatSubButton(backButton);

        add(label);
        add(textField);
        add(createButton);
        add(backButton);
    }

    private void initializeInteraction() {
        NewMenuListener listener = new NewMenuListener();
        textField.addActionListener(listener);
        createButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    private void createNewTrack() {
        String name = textField.getText();
        resetTextField();
        mainMenu.getTracker().addTrack(name);
        mainMenu.openTrack(name);
    }

    public void resetTextField() {
        textField.setText("");
    }

    private class NewMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (createButton.equals(source)) {
                createNewTrack();
            } else if (backButton.equals(source)) {
                mainMenu.goBack();
            }
        }
    }
}
