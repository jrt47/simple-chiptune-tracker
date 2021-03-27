package ui.tracker.taskbar;

import model.Track;
import ui.tracker.TrackerApp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {
    public static final int COMPONENT_HEIGHT = 30;
    private static final int SPACING = 20;

    private TrackerApp trackerApp;

    private JSpinner tempoSpinner;
    private JButton addBarButton;

    public ToolBar(TrackerApp trackerApp) {
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    private void initializeGraphics() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        initializeTempoEditor();
        add(Box.createHorizontalStrut(SPACING));
        initializeBarsEditor();
    }

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

    private void initializeBarsEditor() {
        addBarButton = new JButton("+ Bar");
        addBarButton.setFont(TrackerApp.FONT);
        addBarButton.setPreferredSize(new Dimension(68, COMPONENT_HEIGHT));
        add(addBarButton);
    }

    private void initializeInteraction() {
        tempoSpinner.addChangeListener(new TempoSpinnerListener());
        addBarButton.addActionListener(new AddBarButtonListener());
    }

    private class TempoSpinnerListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            int tempo = (int) tempoSpinner.getValue();
            trackerApp.getTrack().setTempo(tempo);
        }
    }

    private class AddBarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            trackerApp.getTrack().addBars(1);
            trackerApp.getTrackEditor().dataChanged();
        }
    }
}
