package model;

public class Track {
    private String trackName;
    private int tempo;
    private InstrumentChannel pulse1;
    private InstrumentChannel pulse2;
    private InstrumentChannel triangle;
    private InstrumentChannel noise;

    // REQUIRES: trackName has a length greater than 0
    // EFFECTS: creates a new empty 2 bar track with name trackName at 120 BPM
    public Track(String name) {
        trackName = name;
        tempo = 120;
        pulse1 = new InstrumentChannel();
        pulse2 = new InstrumentChannel();
        triangle = new InstrumentChannel();
        noise = new InstrumentChannel();
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTrackName(String name) {
        trackName = name;
    }

    public void setTempo(int bpm) {
        tempo = bpm;
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: adds numBars bars to the track
    public void addBars(int numBars) {
        pulse1.addBars(numBars);
        pulse2.addBars(numBars);
        triangle.addBars(numBars);
        noise.addBars(numBars);
    }

    // REQUIRES: 0 < numBars < numberOfBars()
    // MODIFIES: this
    // EFFECTS: removes numBars bars from the track
    public void removeBars(int numBars) {
        pulse1.removeBars(numBars);
        pulse2.removeBars(numBars);
        triangle.removeBars(numBars);
        noise.removeBars(numBars);
    }

    // EFFECTS: returns the number of rows in the track
    public int numberOfRows() {
        return pulse1.numberOfRows();
    }

    // EFFECTS: returns the number of bars in the track
    public int numberOfBars() {
        return pulse1.numberOfBars();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // EFFECTS: returns the event at the given row in the given channel
    public Event getEvent(String channel, int row) {
        return getChannel(channel).getEvent(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a note with given pitch into given channel at given row
    public void addNote(String channel, int row, int pitch) {
        getChannel(channel).addNote(row, pitch);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a rest into given channel at given row
    public void addRest(String channel, int row) {
        getChannel(channel).addRest(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: makes the note in given channel at given row staccato
    public void makeStaccato(String channel, int row) {
        getChannel(channel).makeStaccato(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: makes the note in given channel at given row not staccato
    public void makeNotStaccato(String channel, int row) {
        getChannel(channel).makeNotStaccato(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: clears the event in given channel at given row
    public void clear(String channel, int row) {
        getChannel(channel).clear(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise",
    //           1 <= startRow/endRow <= numberOfRows(), endRow >= startRow
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel between startRow and endRow (inclusive)
    public void clear(String channel, int startRow, int endRow) {
        getChannel(channel).clear(startRow, endRow);
    }

    // REQUIRES: 1 <= startRow/endRow <= numberOfRows(), endRow >= startRow
    // MODIFIES: this
    // EFFECTS: clears all events between startRow and endRow (inclusive)
    public void clear(int startRow, int endRow) {
        pulse1.clear(startRow, endRow);
        pulse2.clear(startRow, endRow);
        triangle.clear(startRow, endRow);
        noise.clear(startRow, endRow);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel
    public void clear(String channel) {
        getChannel(channel).clear();
    }

    // MODIFIES: this
    // EFFECTS: clears all events in the track
    public void clear() {
        pulse1.clear();
        pulse2.clear();
        triangle.clear();
        noise.clear();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel by numSemitones semitones
    public void transpose(String channel, int numSemitones) {
        getChannel(channel).transpose(numSemitones);
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track by numSemitones semitones
    public void transpose(int numSemitones) {
        pulse1.transpose(numSemitones);
        pulse2.transpose(numSemitones);
        triangle.transpose(numSemitones);
        noise.transpose(numSemitones);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel up by an octave
    public void transposeUpByOctave(String channel) {
        getChannel(channel).transposeUpByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track up by an octave
    public void transposeUpByOctave() {
        pulse1.transposeUpByOctave();
        pulse2.transposeUpByOctave();
        triangle.transposeUpByOctave();
        noise.transposeUpByOctave();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel down by an octave
    public void transposeDownByOctave(String channel) {
        getChannel(channel).transposeDownByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track down by an octave
    public void transposeDownByOctave() {
        pulse1.transposeDownByOctave();
        pulse2.transposeDownByOctave();
        triangle.transposeDownByOctave();
        noise.transposeDownByOctave();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // EFFECTS: returns the channel corresponding to the given string
    private InstrumentChannel getChannel(String channel) {
        switch (channel) {
            case "pulse1":
                return pulse1;
            case "pulse2":
                return pulse2;
            case "triangle":
                return triangle;
            default:
                return noise;
        }
    }
}
