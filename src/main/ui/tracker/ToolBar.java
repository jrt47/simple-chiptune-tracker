package ui.tracker;

import model.Track;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// represents the tracker application toolbar
public class ToolBar extends JPanel {
    public static final int COMPONENT_HEIGHT = 30;
    private static final int MEDIA_CONTROL_BUTTON_WIDTH = 46;
    private static final int SPACING = 20;

    private final TrackerApp trackerApp;

    private JSpinner tempoSpinner;
    private JButton addBarButton;
    private JToggleButton playPauseButton;
    private JButton stopButton;

    // EFFECTS: constructs and initializes the toolbar
    public ToolBar(TrackerApp trackerApp) {
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: resets all media play buttons in the tool bar
    public void resetMediaButtons() {
        playPauseButton.setSelected(false);
        playPauseButton.setText("▶");
    }

    // MODIFIES: this
    // EFFECTS: initializes the toolbar graphics
    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        initializeTempoEditor();
        add(Box.createHorizontalStrut(SPACING));
        initializeBarsEditor();
        add(Box.createHorizontalStrut(SPACING));
        initializePlayPauseButton();
        add(Box.createHorizontalStrut(SPACING / 2));
        initializeStopButton();
    }

    private void initializeStopButton() {
        stopButton = new JButton("⏹");
        stopButton.setPreferredSize(new Dimension(MEDIA_CONTROL_BUTTON_WIDTH, COMPONENT_HEIGHT));
        add(stopButton);
    }

    private void initializePlayPauseButton() {
        playPauseButton = new JToggleButton("▶");
        playPauseButton.setPreferredSize(new Dimension(MEDIA_CONTROL_BUTTON_WIDTH, COMPONENT_HEIGHT));
        add(playPauseButton);
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
        ButtonListener buttonListener = new ButtonListener();

        tempoSpinner.addChangeListener(new TempoSpinnerListener());
        addBarButton.addActionListener(buttonListener);
        playPauseButton.addItemListener(new PlayPauseListener());
        stopButton.addActionListener(buttonListener);
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

    // an action listener for the tool bar buttons
    private class ButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: adds a bar when the add bar button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == addBarButton) {
                trackerApp.getTrack().addBars(1);
                trackerApp.getTrackEditor().dataChanged();
            } else if (source == stopButton) {
                resetMediaButtons();
                trackerApp.getTrackPlayer().stop();
            }
        }
    }

    private class PlayPauseListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                playPauseButton.setText("❚❚");
                trackerApp.getTrackPlayer().play();
            } else if (stateChange == ItemEvent.DESELECTED) {
                playPauseButton.setText("▶");
                trackerApp.getTrackPlayer().pause();
            }
        }
    }
}
