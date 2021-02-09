package model;

public interface Event {

    // MODIFIES: this
    // EFFECTS: transposes event by numSemitones
    void transpose(int numSemitones);

    // MODIFIES: this
    // EFFECTS: transposes event by numOctaves octaves
    void transposeByOctave(int numOctaves);

    // EFFECTS: returns true if the event is a note
    boolean isNote();

    // EFFECTS: returns true if the event is a rest
    boolean isRest();
}
