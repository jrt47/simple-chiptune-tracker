package ui.tracker.menubar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// represents a dialog box that can clear a selection of the selected track
public class ClearDialog extends SelectionDialog {
    private static final int WINDOW_HEIGHT = 200;

    private JCheckBox allRowsCheckBox;
    private JSpinner firstRowSpinner;
    private JSpinner lastRowSpinner;
    private JLabel fromLabel;
    private JLabel toLabel;

    // EFFECTS: constructs and initializes the clear dialog box
    public ClearDialog(TrackerApp trackerApp) {
        super(trackerApp, "Clear", WINDOW_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void initializeSpecificGraphics() {
        allRowsCheckBox = new JCheckBox("All Rows");


        add(Box.createHorizontalStrut(82));
        add(allRowsCheckBox);
        add(Box.createHorizontalStrut(82));

        initializeSpinners();
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void initializeSpecificInteraction() {
        allRowsCheckBox.addItemListener(new AllRowsCheckBoxListener());

        SpinnerListener spinnerListener = new SpinnerListener();
        firstRowSpinner.addChangeListener(spinnerListener);
        lastRowSpinner.addChangeListener(spinnerListener);
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void applyToChannel(String channel) {
        if (allRowsCheckBox.isSelected()) {
            trackerApp.getTrack().clear(channel);
        } else {
            int firstRow = (int) firstRowSpinner.getValue() + 1;
            int lastRow = (int) lastRowSpinner.getValue() + 1;
            trackerApp.getTrack().clear(channel, firstRow, lastRow);
        }
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void applyToAllChannels() {
        if (allRowsCheckBox.isSelected()) {
            trackerApp.getTrack().clear();
        } else {
            int firstRow = (int) firstRowSpinner.getValue();
            int lastRow = (int) lastRowSpinner.getValue();
            trackerApp.getTrack().clear(firstRow, lastRow);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the clear dialog box spinners
    private void initializeSpinners() {
        int maxRow = trackerApp.getTrack().numberOfRows() - 1;

        SpinnerNumberModel firstModel = new SpinnerNumberModel(0, 0, maxRow, 1);
        SpinnerNumberModel lastModel = new SpinnerNumberModel(0, 0, maxRow, 1);

        firstRowSpinner = new JSpinner(firstModel);
        lastRowSpinner = new JSpinner(lastModel);

        fromLabel = new JLabel("From Row:");
        toLabel = new JLabel("To Row:");

        EditMenu.formatSpinner(firstRowSpinner);
        EditMenu.formatSpinner(lastRowSpinner);

        add(fromLabel);
        add(firstRowSpinner);
        add(toLabel);
        add(lastRowSpinner);
    }

    // an item listener for the all rows check box
    private class AllRowsCheckBoxListener implements ItemListener {

        // MODIFIES: this
        // EFFECTS: enables and disables the row selection spinners when the all rows check box is checked
        @Override
        public void itemStateChanged(ItemEvent e) {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                firstRowSpinner.setEnabled(false);
                lastRowSpinner.setEnabled(false);
                fromLabel.setEnabled(false);
                toLabel.setEnabled(false);
            } else if (stateChange == ItemEvent.DESELECTED) {
                firstRowSpinner.setEnabled(true);
                lastRowSpinner.setEnabled(true);
                fromLabel.setEnabled(true);
                toLabel.setEnabled(true);
            }
        }
    }

    // a change listener for the row selection spinners
    private class SpinnerListener implements ChangeListener {

        // MODIFIES: this
        // EFFECTS: continuously edits the spinner values so that the first value is <= second value
        @Override
        public void stateChanged(ChangeEvent e) {
            Object source = e.getSource();
            int firstRowValue = (int) firstRowSpinner.getValue();
            int lastRowValue = (int) lastRowSpinner.getValue();
            if (lastRowValue < firstRowValue) {
                if (source == firstRowSpinner) {
                    lastRowSpinner.setValue(firstRowValue);
                } else if (source == lastRowSpinner) {
                    firstRowSpinner.setValue(lastRowValue);
                }
            }
        }
    }
}