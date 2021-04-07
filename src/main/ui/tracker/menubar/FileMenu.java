package ui.tracker.menubar;

import model.Track;
import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the tracker application file menu in the menu bar
public class FileMenu extends JMenu {
    private final TrackerApp trackerApp;

    private JMenuItem renameButton;
    private JMenuItem quitButton;
    private JMenuItem quitToMenuButton;

    // EFFECTS: constructs and initializes the file menu
    public FileMenu(TrackerApp trackerApp) {
        super("File");
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes the file menu graphics
    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        renameButton = new JMenuItem("Rename");
        quitToMenuButton = new JMenuItem("Save & Quit to Menu");
        quitButton = new JMenuItem("Save & Quit");

        renameButton.setFont(TrackerApp.FONT);
        quitToMenuButton.setFont(TrackerApp.FONT);
        quitButton.setFont(TrackerApp.FONT);

        add(renameButton);
        add(quitToMenuButton);
        add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the file menu components
    private void initializeInteraction() {
        FileMenuListener listener = new FileMenuListener();
        renameButton.addActionListener(listener);
        quitToMenuButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: displays a dialog box that allows the user to rename the current track
    private void rename() {
        Track track = trackerApp.getTrack();
        String name = (String) JOptionPane.showInputDialog(trackerApp, null, "Rename",
                JOptionPane.PLAIN_MESSAGE, null, null, track.getName());
        if (!(name == null) && !name.equals("")) {
            track.setName(name);
            trackerApp.setTitle(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: quits the main tracker application and returns to the main menu
    private void quitToMenu() {
        trackerApp.dispose();
        trackerApp.getMainMenu().goBack();
        trackerApp.getMainMenu().setVisible(true);
    }

    // an action listener for the file menu components
    private class FileMenuListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: performs the required action when a menu item is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == renameButton) {
                rename();
            } else if (source == quitToMenuButton) {
                quitToMenu();
            } else if (source == quitButton) {
                trackerApp.getMainMenu().quit();
            }
        }
    }
}
