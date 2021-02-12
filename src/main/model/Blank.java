package model;

public class Blank implements Event {

    // MODIFIES: this
    // EFFECTS: transpose the note by numSemitones,
    //          if the new pitch is below 1 or above maxPitch,
    //          keep adding or removing octaves until it is in the proper range
    @Override
    public void transpose(int numSemitones) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transpose the note up by an octave
    //          if the new pitch is above maxPitch, do nothing
    @Override
    public void transposeUpByOctave() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transpose the note down by an octave
    //          if the new pitch is below 1, do nothing
    @Override
    public void transposeDownByOctave() {
        // stub
    }

    // EFFECTS: represents the blank as a 6 character string
    @Override
    public String toString() {
        return null; // stub
    }
}
