package ui.tracker.taskbar;

import ui.tracker.TrackerApp;

import javax.swing.*;

public class EditMenu extends JMenu {
    private TrackerApp trackerApp;

    public EditMenu(TrackerApp trackerApp) {
        super("Edit");
        this.trackerApp = trackerApp;
        initializeGraphics();
    }

    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        initializeBarsOptions();
        add(new JSeparator());
        initializeClearOptions();
        add(new JSeparator());
        initializeTransposeOptions();
    }

    private void initializeBarsOptions() {
        JMenuItem addBarsButton = new JMenuItem("Add Bars");
        JMenuItem removeBarsButton = new JMenuItem("Remove Bars");
        JMenuItem setBarsButton = new JMenuItem("Set the Number of Bars");

        addBarsButton.setFont(TrackerApp.FONT);
        removeBarsButton.setFont(TrackerApp.FONT);
        setBarsButton.setFont(TrackerApp.FONT);

        add(addBarsButton);
        add(removeBarsButton);
        add(setBarsButton);
    }

    private void initializeClearOptions() {
        JMenu clearChannelMenu = new JMenu("Clear a Channel");
        initializeClearChannelMenu(clearChannelMenu);
        JMenuItem clearTrackButton = new JMenuItem("Clear the Entire Track");
        JMenuItem clearOptionsButton = new JMenuItem("More Clear Options...");

        clearChannelMenu.setFont(TrackerApp.FONT);
        clearTrackButton.setFont(TrackerApp.FONT);
        clearOptionsButton.setFont(TrackerApp.FONT);

        add(clearChannelMenu);
        add(clearTrackButton);
        add(clearOptionsButton);
    }

    private void initializeTransposeOptions() {
        JMenuItem transposeButton = new JMenuItem("Transpose");
        transposeButton.setFont(TrackerApp.FONT);
        add(transposeButton);
    }

    private static void initializeClearChannelMenu(JComponent clearChannelMenu) {
        JMenuItem pulse1Button = new JMenuItem("Pulse 1");
        JMenuItem pulse2Button = new JMenuItem("Pulse 2");
        JMenuItem triangleButton = new JMenuItem("Triangle");
        JMenuItem noiseButton = new JMenuItem("Noise");

        pulse1Button.setFont(TrackerApp.FONT);
        pulse2Button.setFont(TrackerApp.FONT);
        triangleButton.setFont(TrackerApp.FONT);
        noiseButton.setFont(TrackerApp.FONT);

        clearChannelMenu.add(pulse1Button);
        clearChannelMenu.add(pulse2Button);
        clearChannelMenu.add(triangleButton);
        clearChannelMenu.add(noiseButton);
    }
}
