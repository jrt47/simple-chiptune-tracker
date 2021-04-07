package ui.sound;

import model.Event;
import model.Track;
import ui.tracker.ToolBar;
import ui.tracker.TrackEditor;
import ui.tracker.TrackerApp;

import java.util.Timer;
import java.util.TimerTask;

// Represents a track player that plays the current track open in the tracker app
public class TrackPlayer {
    private final TrackEditor trackEditor;
    private final ToolBar toolBar;

    private final Track track;
    private Timer timer;

    private int curRow;

    private boolean pulse1PrevWasStaccato;
    private boolean pulse2PrevWasStaccato;
    private boolean trianglePrevWasStaccato;
    private boolean noisePrevWasStaccato;

    private final Oscillator pulse1Oscillator;
    private final Oscillator pulse2Oscillator;
    private final Oscillator triangleOscillator;
    private final Oscillator noiseOscillator;

    // EFFECTS: constructs and initializes the track player
    public TrackPlayer(TrackerApp trackerApp) {
        trackEditor = trackerApp.getTrackEditor();
        toolBar = trackerApp.getToolBar();
        track = trackerApp.getTrack();
        curRow = 1;

        pulse1Oscillator = new PulseOscillator(25);
        pulse2Oscillator = new PulseOscillator(50);
        triangleOscillator = new TriangleOscillator();
        noiseOscillator = new NoiseOscillator();
    }

    // MODIFIES: this
    // EFFECTS: plays the selected track to the user
    public void play() {
        curRow = trackEditor.getSelectedRow() + 1;
        timer = new Timer();
        trackEditor.setColumnSelectionInterval(0, 0);
        long period = 60 * 1000 / (4 * track.getTempo());
        timer.scheduleAtFixedRate(new PlayEventTask(), 0, period);
    }

    // MODIFIES: this
    // EFFECTS: pauses the selected track if it is currently being played
    public void pause() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            pulse1Oscillator.stop();
            pulse2Oscillator.stop();
            triangleOscillator.stop();
            noiseOscillator.stop();
        }
    }

    // MODIFIES: this
    // EFFECTS: stops the currently selected track if it is being played and selects the first row in the track editor
    public void stop() {
        pause();
        curRow = 1;
        trackEditor.setRowSelectionInterval(0, 0);
    }

    // MODIFIES: this
    // EFFECTS: plays the event at the current row in the given channel to the user
    private void playEventAtCurRow(String channel) {
        Event event = track.getEvent(channel, curRow);
        String type = event.getType();
        boolean prevWasStaccato = getPrevWasStaccato(channel);
        if (type.equals("blank") && !prevWasStaccato) {
            return;
        }
        Oscillator oscillator = getChannelOscillator(channel);
        if (type.equals("note")) {
            oscillator.play(event.getPitch());
            if (event.getIsStaccato()) {
                setCurIsStaccato(channel, true);
            } else {
                setCurIsStaccato(channel, false);
            }
        } else if (type.equals("rest") || prevWasStaccato) {
            oscillator.stop();
            setCurIsStaccato(channel, false);
        }
    }

    // EFFECTS: returns true if the previous note in the given channel was staccato
    private boolean getPrevWasStaccato(String channel) {
        switch (channel) {
            case "pulse1":
                return pulse1PrevWasStaccato;
            case "pulse2":
                return pulse2PrevWasStaccato;
            case "triangle":
                return trianglePrevWasStaccato;
            case "noise":
                return noisePrevWasStaccato;
            default:
                return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if isStaccato is true, notify the track player that the current note is staccato,
    //          if isStaccato is false, notify the track player that the current note is not staccato
    private void setCurIsStaccato(String channel, boolean isStaccato) {
        switch (channel) {
            case "pulse1":
                pulse1PrevWasStaccato = isStaccato;
                break;
            case "pulse2":
                pulse2PrevWasStaccato = isStaccato;
                break;
            case "triangle":
                trianglePrevWasStaccato = isStaccato;
                break;
            case "noise":
                noisePrevWasStaccato = isStaccato;
                break;
        }
    }

    // EFFECTS: returns the oscillator assigned to the given channel
    private Oscillator getChannelOscillator(String channel) {
        Oscillator oscillator = null;
        switch (channel) {
            case "pulse1":
                oscillator = pulse1Oscillator;
                break;
            case "pulse2":
                oscillator = pulse2Oscillator;
                break;
            case "triangle":
                oscillator = triangleOscillator;
                break;
            case "noise":
                oscillator = noiseOscillator;
                break;
        }
        return oscillator;
    }

    // A timer task that plays the current events
    private class PlayEventTask extends TimerTask {

        // MODIFIES: TrackPlayer.this
        // EFFECTS: plays the event at the given row in all channels and stops playback when the end of the
        //          track is reached
        @Override
        public void run() {
            if (curRow <= track.numberOfRows()) {
                playEventAtCurRow("noise");
                playEventAtCurRow("triangle");
                playEventAtCurRow("pulse1");
                playEventAtCurRow("pulse2");
                trackEditor.setRowSelectionInterval(curRow - 1, curRow - 1);
                curRow++;
            } else {
                stop();
                toolBar.resetMediaButtons();
            }
        }
    }
}
