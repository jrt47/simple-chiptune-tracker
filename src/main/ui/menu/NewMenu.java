package ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the new menu card for the tracker application menu
public class NewMenu extends JPanel {
    private MainMenu mainMenu;

    private JTextField textField;
    private JButton createButton;
    private JButton backButton;

    // EFFECTS: constructs and initializes the new menu
    public NewMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes graphics for the new menu
    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.CENTER, MainMenu.SPACING, MainMenu.SPACING));

        JLabel label = new JLabel("Track Title:");
        textField = new JTextField(18);
        createButton = new JButton("Create");
        backButton = new JButton("Back");

        label.setFont(MainMenu.FONT);
        MainMenu.formatTextComponent(textField);
        MainMenu.formatSubButton(createButton);
        MainMenu.formatSubButton(backButton);

        add(label);
        add(textField);
        add(createButton);
        add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the new menu components
    private void initializeInteraction() {
        NewMenuListener listener = new NewMenuListener();
        textField.addActionListener(listener);
        createButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: creates and opens a new track with the user-entered name
    private void createNewTrack() {
        String name = textField.getText();
        if (!name.equals("")) {
            resetTextField();
            mainMenu.getTracker().addTrack(name);
            mainMenu.openTrack(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the track name text field
    public void resetTextField() {
        textField.setText("");
    }

    // action listener for the new menu components
    private class NewMenuListener implements ActionListener {

        // MODIFIES: NewMenu.this
        // EFFECTS: performs the required operation when a button is pressed
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
