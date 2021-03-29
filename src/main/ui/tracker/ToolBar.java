package ui.tracker;

import model.Track;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the tracker application toolbar
public class ToolBar extends JPanel {
    public static final int COMPONENT_HEIGHT = 30;
    private static final int SPACING = 20;

    private TrackerApp trackerApp;

    private JSpinner tempoSpinner;
    private JButton addBarButton;

    // EFFECTS: constructs and initializes the toolbar
    public ToolBar(TrackerApp trackerApp) {
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes the toolbar graphics
    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        initializeTempoEditor();
        add(Box.createHorizontalStrut(SPACING));
        initializeBarsEditor();
    }

    // MODIFIES: this
    // EFFECTS: initializes the tempo editor tool
    private void initializeTempoEditor() {
        int tempo = trackerApp.getTrack().getTempo();
        SpinnerNumberModel model = new SpinnerNumberModel(tempo, Track.MIN_BPM, Track.MAX_BPM, 1);
        tempoSpinner = new JSpinner(model);
        JLabel bpmLabel = new JLabel("BPM");

        tempoSpinner.setPreferredSize(new Dimension(45, COMPONENT_HEIGHT));
        tempoSpinner.setFont(TrackerApp.FONT);
        bpmLabel.setFont(TrackerApp.FONT);

        add(tempoSpinner);
        add(bpmLabel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the add bar tool
    private void initializeBarsEditor() {
        addBarButton = new JButton("+ Bar");
        addBarButton.setFont(TrackerApp.FONT);
        addBarButton.setPreferredSize(new Dimension(68, COMPONENT_HEIGHT));
        add(addBarButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the toolbar components
    private void initializeInteraction() {
        tempoSpinner.addChangeListener(new TempoSpinnerListener());
        addBarButton.addActionListener(new AddBarButtonListener());
    }

    // a change listener for the toolbar tempo spinner
    private class TempoSpinnerListener implements ChangeListener {

        // MODIFIES: this
        // EFFECTS: sets the tempo when the tempo spinner value is changed
        @Override
        public void stateChanged(ChangeEvent e) {
            int tempo = (int) tempoSpinner.getValue();
            trackerApp.getTrack().setTempo(tempo);
        }
    }

    // an action listener for the add bar button
    private class AddBarButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: adds a bar when the add bar button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            trackerApp.getTrack().addBars(1);
            trackerApp.getTrackEditor().dataChanged();
        }
    }
}
