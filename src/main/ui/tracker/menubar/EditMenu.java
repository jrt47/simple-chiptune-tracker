package ui.tracker.menubar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the tracker application edit menu in the menu bar
public class EditMenu extends JMenu {
    public static final Dimension SPINNER_SIZE = new Dimension(45, 28);
    public static final Dimension BUTTON_SIZE = new Dimension(78, 28);
    public static final int SPACING = 10;

    private TrackerApp trackerApp;

    private JMenuItem addBarsButton;
    private JMenuItem removeBarsButton;
    private JMenuItem setBarsButton;

    private JMenuItem clearTrackButton;
    private JMenuItem clearPulse1Button;
    private JMenuItem clearPulse2Button;
    private JMenuItem clearTriangleButton;
    private JMenuItem clearNoiseButton;
    private JMenuItem clearOptionsButton;

    private JMenuItem transposeButton;

    // EFFECTS: constructs and initializes the edit menu
    public EditMenu(TrackerApp trackerApp) {
        super("Edit");
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the edit menu components
    private void initializeInteraction() {
        EditMenuActionListener listener = new EditMenuActionListener();
        addBarsButton.addActionListener(listener);
        removeBarsButton.addActionListener(listener);
        setBarsButton.addActionListener(listener);
        clearTrackButton.addActionListener(listener);
        clearPulse1Button.addActionListener(listener);
        clearPulse2Button.addActionListener(listener);
        clearTriangleButton.addActionListener(listener);
        clearNoiseButton.addActionListener(listener);
        clearOptionsButton.addActionListener(listener);
        transposeButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics for the edit menu
    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        initializeBarsOptions();
        add(new JSeparator());
        initializeClearOptions();
        add(new JSeparator());
        initializeTransposeOptions();
    }

    // MODIFIES: this
    // EFFECTS: initializes the options to edit the number of bars
    private void initializeBarsOptions() {
        addBarsButton = new JMenuItem("Add Bars");
        removeBarsButton = new JMenuItem("Remove Bars");
        setBarsButton = new JMenuItem("Set the # of Bars");

        addBarsButton.setFont(TrackerApp.FONT);
        removeBarsButton.setFont(TrackerApp.FONT);
        setBarsButton.setFont(TrackerApp.FONT);

        add(addBarsButton);
        add(removeBarsButton);
        add(setBarsButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes options to clear the track
    private void initializeClearOptions() {
        JMenu clearChannelMenu = new JMenu("Clear a Channel");
        initializeClearChannelMenu(clearChannelMenu);
        clearTrackButton = new JMenuItem("Clear All Channels");
        clearOptionsButton = new JMenuItem("More Clear Options...");

        clearChannelMenu.setFont(TrackerApp.FONT);
        clearTrackButton.setFont(TrackerApp.FONT);
        clearOptionsButton.setFont(TrackerApp.FONT);

        add(clearChannelMenu);
        add(clearTrackButton);
        add(clearOptionsButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes options to transpose the track
    private void initializeTransposeOptions() {
        transposeButton = new JMenuItem("Transpose");
        transposeButton.setFont(TrackerApp.FONT);
        add(transposeButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes the clear channel sub-menu
    private void initializeClearChannelMenu(JComponent clearChannelMenu) {
        clearPulse1Button = new JMenuItem("Pulse 1");
        clearPulse2Button = new JMenuItem("Pulse 2");
        clearTriangleButton = new JMenuItem("Triangle");
        clearNoiseButton = new JMenuItem("Noise");

        clearPulse1Button.setFont(TrackerApp.FONT);
        clearPulse2Button.setFont(TrackerApp.FONT);
        clearTriangleButton.setFont(TrackerApp.FONT);
        clearNoiseButton.setFont(TrackerApp.FONT);

        clearChannelMenu.add(clearPulse1Button);
        clearChannelMenu.add(clearPulse2Button);
        clearChannelMenu.add(clearTriangleButton);
        clearChannelMenu.add(clearNoiseButton);
    }

    // MODIFIES: this
    // EFFECTS: displays the remove bars dialog box if there are enough bars to remove,
    //          otherwise display an error message
    private void removeBarsIfEnough() {
        if (trackerApp.getTrack().numberOfBars() > 2) {
            new BarsDialog(trackerApp, "remove");
        } else {
            JOptionPane.showMessageDialog(trackerApp, "Not enough bars to remove.", "Remove Bars",
                    JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    // MODIFIES: spinner
    // EFFECTS: formats the spinner according to the edit menu specifications
    public static void formatSpinner(JSpinner spinner) {
        spinner.setPreferredSize(EditMenu.SPINNER_SIZE);
        spinner.setFont(TrackerApp.FONT);
    }

    // an action listener for the edit menu components
    private class EditMenuActionListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: performs the required action when a menu item is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(addBarsButton)) {
                new BarsDialog(trackerApp, "add");
            } else if (source.equals(removeBarsButton)) {
                removeBarsIfEnough();
            } else if (source.equals(setBarsButton)) {
                new BarsDialog(trackerApp, "set");
            } else if (source.equals(clearTrackButton)) {
                trackerApp.getTrack().clear();
            } else if (source.equals(clearPulse1Button)) {
                trackerApp.getTrack().clear("pulse1");
            } else if (source.equals(clearPulse2Button)) {
                trackerApp.getTrack().clear("pulse2");
            } else if (source.equals(clearTriangleButton)) {
                trackerApp.getTrack().clear("triangle");
            } else if (source.equals(clearNoiseButton)) {
                trackerApp.getTrack().clear("noise");
            } else if (source.equals(clearOptionsButton)) {
                new ClearDialog(trackerApp);
            } else if (source.equals(transposeButton)) {
                new TransposeDialog(trackerApp);
            }
            trackerApp.getTrackEditor().dataChanged();
        }
    }
}
