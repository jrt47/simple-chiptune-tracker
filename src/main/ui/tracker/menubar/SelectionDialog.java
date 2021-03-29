package ui.tracker.menubar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a dialog box which allows the user to select a channel in the track (or all channels)
public abstract class SelectionDialog extends JDialog {
    private static final int WINDOW_WIDTH = 280;
    private static final String[] CHANNEL_OPTIONS = {"All Channels", "Pulse 1", "Pulse 2", "Triangle", "Noise"};

    protected TrackerApp trackerApp;

    private JComboBox<String> channelSelector;
    private JButton okButton;
    private JButton cancelButton;

    // EFFECTS: constructs and initializes the selection dialog box
    public SelectionDialog(TrackerApp trackerApp, String title, int windowHeight) {
        super(trackerApp, title);
        this.trackerApp = trackerApp;
        initializeGraphics(windowHeight);
        initializeInteraction();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the dialog box graphics
    private void initializeGraphics(int windowHeight) {
        setLayout(new FlowLayout(FlowLayout.CENTER, EditMenu.SPACING, EditMenu.SPACING));
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(WINDOW_WIDTH, windowHeight));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: initializes the dialog box components
    protected void initializeComponents() {
        initializeChannelSelection();
        initializeSpecificGraphics();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics that are specific to each selection dialog box
    protected abstract void initializeSpecificGraphics();

    // MODIFIES: this
    // EFFECTS: initializes the components which allow the user to select a channel
    private void initializeChannelSelection() {
        JLabel channelLabel = new JLabel("Channel:");
        channelSelector = new JComboBox<>(CHANNEL_OPTIONS);

        add(channelLabel);
        add(channelSelector);
    }

    // MODIFIES: this
    // EFFECTS: initializes the OK and cancel buttons at the bottom of the dialog box
    private void initializeButtons() {
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.setPreferredSize(EditMenu.BUTTON_SIZE);
        cancelButton.setPreferredSize(EditMenu.BUTTON_SIZE);

        add(okButton);
        add(cancelButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the dialog box components
    private void initializeInteraction() {
        SelectionButtonListener selectionButtonListener = new SelectionButtonListener();
        okButton.addActionListener(selectionButtonListener);
        cancelButton.addActionListener(selectionButtonListener);

        initializeSpecificInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction that is specific to each dialog box
    protected abstract void initializeSpecificInteraction();

    // MODIFIES: this
    // EFFECTS: performs the required action when the ok button is pressed
    private void okButtonHit() {
        String channelSelection = (String) channelSelector.getSelectedItem();
        if (channelSelection == null) {
            return;
        }
        switch (channelSelection) {
            case "All Channels":
                applyToAllChannels();
                break;
            case "Pulse 1":
                applyToChannel("pulse1");
                break;
            case "Pulse 2":
                applyToChannel("pulse2");
                break;
            case "Triangle":
                applyToChannel("triangle");
                break;
            case "Noise":
                applyToChannel("noise");
                break;
        }
        trackerApp.getTrackEditor().dataChanged();
    }

    // MODIFIES: this
    // EFFECTS: applies the required operation to the given channel
    protected abstract void applyToChannel(String channel);

    // MODIFIES: this
    // EFFECTS: applies the required operation to all channels
    protected abstract void applyToAllChannels();

    // an action listener for the buttons in the dialog box
    private class SelectionButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: performs the required operation when a button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == okButton) {
                okButtonHit();
                dispose();
            } else if (source == cancelButton) {
                dispose();
            }
        }
    }
}
