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
    // EFFECTS: transpose the note up by an octave
    //          if the new pitch is above maxPitch,
    //          keep removing octaves until it is in the proper range
    @Override
    public void transposeUpByOctave() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: transpose the note down by an octave
    //          if the new pitch is below 1,
    //          keep adding octaves until it is in the proper range
    @Override
    public void transposeDownByOctave() {
        // stub
    }

    // EFFECTS: represents the rest as a 6 character string
    @Override
    public String toString() {
        return null; // stub
    }
}
