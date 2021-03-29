package ui.tracker.menubar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// represents a selection dialog box that allows the user to transpose parts of the track
public class TransposeDialog extends SelectionDialog {
    private static final int WINDOW_HEIGHT = 232;

    private JRadioButton downButton;
    private JCheckBox octaveCheckBox;
    private JSpinner semitoneSpinner;

    private JLabel byLabel;
    private JLabel semitonesLabel;

    // EFFECTS: constructs and initializes the transpose dialog box
    public TransposeDialog(TrackerApp trackerApp) {
        super(trackerApp, "Transpose", WINDOW_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void initializeSpecificGraphics() {
        initializeRadioButtons();
        initializeOctaveCheckBox();
        initializeSemitoneSelector();
    }

    // MODIFIES: this
    // EFFECTS: initializes the octave check box component
    private void initializeOctaveCheckBox() {
        octaveCheckBox = new JCheckBox("By An Octave");
        add(Box.createHorizontalStrut(65));
        add(octaveCheckBox);
        add(Box.createHorizontalStrut(65));
    }

    // MODIFIES: this
    // EFFECTS: initializes the number of semitones selector component
    private void initializeSemitoneSelector() {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 11, 1);
        semitoneSpinner = new JSpinner(model);
        semitoneSpinner.setFont(TrackerApp.FONT);

        byLabel = new JLabel("By");
        semitonesLabel = new JLabel("Semitones");

        add(Box.createHorizontalStrut(40));
        add(byLabel);
        add(semitoneSpinner);
        add(semitonesLabel);
        add(Box.createHorizontalStrut(40));
    }

    // MODIFIES: this
    // EFFECTS: initializes the up/down radio button components
    private void initializeRadioButtons() {
        JRadioButton upButton = new JRadioButton("Transpose Up", true);
        downButton = new JRadioButton("Transpose Down", false);

        add(upButton);
        add(downButton);

        ButtonGroup group = new ButtonGroup();
        group.add(upButton);
        group.add(downButton);
    }

    // EFFECTS: returns the number of semitones to transpose by based on the user's current selection
    private int getNumSemitones() {
        int numSemitones;
        if (octaveCheckBox.isSelected()) {
            numSemitones = 12;
        } else {
            numSemitones = (int) semitoneSpinner.getValue();
        }
        if (downButton.isSelected()) {
            numSemitones = -numSemitones;
        }
        return numSemitones;
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void initializeSpecificInteraction() {
        octaveCheckBox.addItemListener(new OctaveCheckBoxListener());
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void applyToChannel(String channel) {
        trackerApp.getTrack().transpose(channel, getNumSemitones());
    }

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void applyToAllChannels() {
        trackerApp.getTrack().transpose(getNumSemitones());
    }

    // an item listener for the octave check box
    private class OctaveCheckBoxListener implements ItemListener {

        // MODIFIES: this
        // EFFECTS: enables/disables the semitone selection components when the octave checkbox is checked
        @Override
        public void itemStateChanged(ItemEvent e) {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                semitoneSpinner.setEnabled(false);
                byLabel.setEnabled(false);
                semitonesLabel.setEnabled(false);
            } else if (stateChange == ItemEvent.DESELECTED) {
                semitoneSpinner.setEnabled(true);
                byLabel.setEnabled(true);
                semitonesLabel.setEnabled(true);
            }
        }
    }
}
