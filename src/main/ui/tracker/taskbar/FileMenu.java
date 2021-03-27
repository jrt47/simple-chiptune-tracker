package ui.tracker.taskbar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileMenu extends JMenu {
    private TrackerApp trackerApp;

    private JMenuItem renameButton;
    private JMenuItem quitButton;

    public FileMenu(TrackerApp trackerApp) {
        super("File");
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        renameButton = new JMenuItem("Rename");
        quitButton = new JMenuItem("Save & Quit");

        renameButton.setFont(TrackerApp.FONT);
        quitButton.setFont(TrackerApp.FONT);

        add(renameButton);
        add(quitButton);
    }

    private void initializeInteraction() {
        FileMenuListener listener = new FileMenuListener();
        renameButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    private void quitToMenu() {
        trackerApp.setVisible(false);
        trackerApp.getMainMenu().setVisible(true);
        trackerApp.getMainMenu().goBack();
    }

    private class FileMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == renameButton) {
                // TODO
            } else if (source == quitButton) {
                quitToMenu();
            }
        }
    }
}
