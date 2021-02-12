package model;

public class InstrumentChannel {

    // EFFECTS: constructs an empty 2 bar instrument channel
    public InstrumentChannel() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: places given note into channel at given row, if row does not exist do nothing
    public void addEvent(int row, Event event) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: clear the event in the channel at the given row, if row does not exist do nothing
    public void clearEvent(int row) {
        // stub
    }

    // REQUIRES: startRow/endRow >= 1
    // MODIFIES: this
    // EFFECTS: clears all events in the channel between startRow and endRow (inclusive),
    //          do nothing to rows that do not exist
    public void clear(int startRow, int endRow) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: clears all events in the channel
    public void clear() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the channel by numSemitones semitones
    public void transpose(int numSemitones) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the channel by numOctaves octaves
    public void transposeByOctave(int numOctaves) {
        // stub
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: adds numBars bars to the channel
    public void addBars(int numBars) {
        // stub
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: removes numBars bars from the channel
    public void removeBars(int numBars) {
        // stub
    }

    // EFFECTS: returns the number of bars in the channel
    public int numberOfBars() {
        return 0; // stub
    }
}
