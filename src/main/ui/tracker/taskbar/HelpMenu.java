package ui.tracker.taskbar;

import ui.tracker.TrackerApp;

import javax.swing.*;

public class HelpMenu extends JMenu {
    private TrackerApp trackerApp;

    public HelpMenu(TrackerApp trackerApp) {
        super("Help");
        this.trackerApp = trackerApp;
        initializeGraphics();
    }

    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        JMenuItem helpButton = new JMenuItem("Get Help");
        helpButton.setFont(TrackerApp.FONT);

        add(helpButton);
    }
}
