package model;

public interface Event {

    // MODIFIES: this
    // EFFECTS: transpose the note by numSemitones,
    //          if the new pitch is below 1 or above maxPitch,
    //          keep adding or removing octaves until it is in the proper range
    void transpose(int numSemitones);

    // MODIFIES: this
    // EFFECTS: transpose the note up by an octave
    //          if the new pitch is above maxPitch,
    //          keep removing octaves until it is in the proper range
    void transposeUpByOctave();

    // MODIFIES: this
    // EFFECTS: transpose the note down by an octave
    //          if the new pitch is below 1,
    //          keep adding octaves until it is in the proper range
    void transposeDownByOctave();
}
