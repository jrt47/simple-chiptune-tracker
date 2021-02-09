package model;

public class Track {

    // REQUIRES: trackName has a length greater than 0
    // EFFECTS: creates a new empty 2 bar track with name trackName
    public Track(String trackName) {
        // stub
    }

    // getters:
    public int getTrackName() {
        return 0; // stub
    }

    public int getTempo() {
        return 0; // stub
    }

    // setters:
    public void setTrackName(String name) {
        // stub
    }

    public void setTempo(int bpm) {
        // stub
    }

    // REQUIRES: 1 <= channel <= 4; row >= 1
    // MODIFIES: this
    // EFFECTS: places given note into given channel at given row, if row does not exist do nothing
    public void addEvent(int channel, int row, Event event) {
        // stub
    }

    // REQUIRES: 1 <= channel <= 4; row >= 1
    // MODIFIES: this
    // EFFECTS: clears the event in the given channel at the given row, if row does not exist do nothing
    public void clearEvent(int channel, int row) {
        // stub
    }

    // REQUIRES: 1 <= channel <= 4; startRow/endRow >= 1; endRow > startRow
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel between startRow and endRow (inclusive),
    //          do nothing to rows that do not exist
    public void clear(int channel, int startRow, int endRow) {
        // stub
    }

    // REQUIRES: startRow/endRow >= 1
    // MODIFIES: this
    // EFFECTS: clears all events in the track between startRow and endRow (inclusive),
    //          do nothing to rows that do not exist
    public void clearRows(int startRow, int endRow) {
        // stub
    }

    // REQUIRES: 1 <= channel <= 4
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel
    public void clearChannel(int channel) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: clears all events in the track
    public void clearAll() {
        // stub
    }

    // REQUIRES: 1 <= channel <= 4
    // MODIFIES: this
    // EFFECTS: transposes all the notes in given channel by numSemitones
    public void transposeChannel(int channel, int numSemitones) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes all the notes in the track by numSemitones
    public void transposeAll(int numSemitones) {
        // stub
    }

    // REQUIRES 1 <= channel <= 4
    // MODIFIES: this
    // EFFECTS: transposes all the notes in given channel by numOctaves octaves
    public void transposeChannelByOctave(int channel, int numOctaves) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes all the notes in the track by numOctaves octaves
    public void transposeAllByOctave(int numOctaves) {
        // stub
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: adds numBars bars to the track
    public void addBars(int numBars) {
        // stub
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: removes numBars bars from the track
    public void removeBars(int numBars) {
        // stub
    }

    // EFFECTS: returns the number of bars in the track
    public int numberOfBars() {
        return 0; // return
    }
}
