package model;

public class Rest implements Event {

    // EFFECTS: constructs a rest
    public Rest() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes event by numSemitones
    @Override
    public void transpose(int numSemitones) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transposes event by numOctaves octaves
    @Override
    public void transposeByOctave(int numOctaves) {
        // stub
    }

    // EFFECTS: returns true if the event is a note
    @Override
    public boolean isNote() {
        return false; // stub
    }

    // EFFECTS: returns true if the event is a rest
    @Override
    public boolean isRest() {
        return false; // stub
    }
}
