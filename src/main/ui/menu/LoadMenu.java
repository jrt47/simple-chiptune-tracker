package ui.menu;

import model.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadMenu extends JPanel {
    private MainMenu mainMenu;

    private JComboBox<String> comboBox;
    private JButton openButton;
    private JButton deleteButton;
    private JButton backButton;

    public LoadMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.CENTER, MainMenu.SPACING, MainMenu.SPACING));

        JLabel label = new JLabel("Select Track:");
        comboBox = new JComboBox<>();
        openButton = new JButton("Open");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        label.setFont(MainMenu.FONT);
        MainMenu.formatTextBox(comboBox);
        MainMenu.formatSubButton(openButton);
        MainMenu.formatSubButton(deleteButton);
        MainMenu.formatSubButton(backButton);

        add(label);
        add(comboBox);
        add(openButton);
        add(deleteButton);
        add(backButton);
    }

    private void initializeInteraction() {
        LoadMenuListener listener = new LoadMenuListener();
        comboBox.addActionListener(listener);
        openButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    public void updateTracks() {
        comboBox.removeAllItems();
        List<Track> trackList = mainMenu.getTracker().getTrackList();
        for (Track track : trackList) {
            comboBox.addItem(track.getName());
        }
    }

    private void openTrack() {
        String name = (String) comboBox.getSelectedItem();
        mainMenu.openTrack(name);
    }

    private void deleteTrack() {
        String name = (String) comboBox.getSelectedItem();
        mainMenu.getTracker().removeTrack(name);
        updateTracks();
    }

    private class LoadMenuListener implements ActionListener {

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
