package ui.menu;

import model.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents the load menu card for the tracker application menu
public class LoadMenu extends JPanel {
    private MainMenu mainMenu;

    private JComboBox<String> comboBox;
    private JButton openButton;
    private JButton deleteButton;
    private JButton backButton;

    // EFFECTS: constructs and initializes the load menu
    public LoadMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes the load menu graphics
    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.CENTER, MainMenu.SPACING, MainMenu.SPACING));

        JLabel label = new JLabel("Select Track:");
        comboBox = new JComboBox<>();
        openButton = new JButton("Open");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        label.setFont(MainMenu.FONT);
        MainMenu.formatTextComponent(comboBox);
        MainMenu.formatSubButton(openButton);
        MainMenu.formatSubButton(deleteButton);
        MainMenu.formatSubButton(backButton);

        add(label);
        add(comboBox);
        add(openButton);
        add(deleteButton);
        add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for all load menu components
    private void initializeInteraction() {
        LoadMenuListener listener = new LoadMenuListener();
        comboBox.addActionListener(listener);
        openButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: updates the track list to be displayed by the track selector combo box
    public void updateTracks() {
        comboBox.removeAllItems();
        List<Track> trackList = mainMenu.getTracker().getTrackList();
        for (Track track : trackList) {
            comboBox.addItem(track.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: opens the selected track
    private void openTrack() {
        String name = (String) comboBox.getSelectedItem();
        mainMenu.openTrack(name);
    }

    // MODIFIES: this
    // EFFECTS: deletes the selected track
    private void deleteTrack() {
        String name = (String) comboBox.getSelectedItem();
        mainMenu.getTracker().removeTrack(name);
        updateTracks();
    }

    // action listener for the load menu components
    private class LoadMenuListener implements ActionListener {

        // MODIFIES: LoadMenu.this
        // EFFECTS: performs the required operation when a button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (openButton.equals(source)) {
                openTrack();
            } else if (deleteButton.equals(source)) {
                deleteTrack();
            } else if (backButton.equals(source)) {
                mainMenu.goBack();
            }
        }
    }
}
