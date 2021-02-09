package model;

public class Note implements Event {

    // REQUIRES: 0 <= pitch <= maxPitch
    // EFFECTS: constructs a new note with given pitch which is staccato if isStaccato is true
    public Note(int pitch, boolean isStaccato) {
        // stub
    }

    // getters:
    public int getPitch() {
        return 0; // stub
    }

    public boolean getIsStaccato() {
        return false; // stub
    }

    // setters:
    public void setPitch(int pitch) {
        // stub
    }

    public void setIsStaccato(boolean isStaccato) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transpose the note by numSemitones
    @Override
    public void transpose(int numSemitones) {
        // stub
    }

    // MODIFIES: this
    @Override
    public void transposeByOctave(int numOctaves) {
        // stub
    }

    // EFFECTS: returns true if the event is a note
    @Override
    public boolean isNote() {
        return true; // stub
    }

    // EFFECTS: returns true if the even is a rest
    @Override
    public boolean isRest() {
        return false;
    }
}
