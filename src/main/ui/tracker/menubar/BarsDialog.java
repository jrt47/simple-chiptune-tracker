package ui.tracker.menubar;

import model.Track;
import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a dialog window that can edit the number of bars
public class BarsDialog extends JDialog {
    private static final Dimension WINDOW_SIZE = new Dimension(220, 120);

    private final TrackerApp trackerApp;
    private final String type;

    private JSpinner spinner;
    private JButton okButton;
    private JButton cancelButton;

    // EFFECTS: constructs and initializes the bar dialog window according to the specified type
    public BarsDialog(TrackerApp trackerApp, String type) {
        super(trackerApp, generateTitle(type));
        this.trackerApp = trackerApp;
        this.type = type;
        initializeGraphics();
        initializeInteraction();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    // EFFECTS: generates the dialog box title based on the given type
    private static String generateTitle(String type) {
        switch (type) {
            case "add":
                return "Add Bars";
            case "remove":
                return "Remove Bars";
            case "set":
                return "# of Bars";
        }
        return "";
    }

    // MODIFIES: this
    // EFFECTS: initializes the dialog box graphics
    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.CENTER, EditMenu.SPACING, EditMenu.SPACING));
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(WINDOW_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: initializes the dialog box components
    private void initializeComponents() {
        SpinnerNumberModel model;
        int numBars = trackerApp.getTrack().numberOfBars();
        if (type.equals("add")) {
            model = new SpinnerNumberModel(1, 1, 60 - numBars, 1);
        } else if (type.equals("remove")) {
            model = new SpinnerNumberModel(1, 1, numBars - 2, 1);
        } else {
            model = new SpinnerNumberModel(numBars, 2, 60, 1);
        }
        spinner = new JSpinner(model);
        EditMenu.formatSpinner(spinner);

        okButton = new JButton("OK");
        okButton.setPreferredSize(EditMenu.BUTTON_SIZE);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(EditMenu.BUTTON_SIZE);

        add(Box.createHorizontalStrut(40));
        add(spinner);
        add(Box.createHorizontalStrut(40));
        add(okButton);
        add(cancelButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for dialog box components
    private void initializeInteraction() {
        BarsDialogListener listener = new BarsDialogListener();
        okButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: changes the number of bars according to the dialog box's type
    private void changeBars() {
        int numBars = (int) spinner.getValue();
        Track track = trackerApp.getTrack();
        switch (type) {
            case "add":
                track.addBars(numBars);
                break;
            case "remove":
                track.removeBars(numBars);
                break;
            case "set":
                int curBars = track.numberOfBars();
                if (curBars < numBars) {
                    track.addBars(numBars - curBars);
                } else if (curBars > numBars) {
                    track.removeBars(curBars - numBars);
                }
                break;
        }
        trackerApp.getTrackEditor().dataChanged();
        dispose();
    }

    // an action listener for the bars editor dialog box buttons
    private class BarsDialogListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: performs the required action when a button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(okButton)) {
                changeBars();
            } else if (source.equals(cancelButton)) {
                dispose();
            }
        }
    }
}
