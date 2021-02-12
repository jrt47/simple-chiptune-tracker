package model;

public class StaccatoNote extends Note {

    // REQUIRES: 1 <= pitch <= maxPitch
    // EFFECTS: constructs a legato note with given pitch
    public StaccatoNote(int pitch) {
        super(pitch);
    }

    // EFFECTS: returns the 1 character representation of the effect on the note (if no effect, return "_")
    @Override
    protected String getEffect() {
        return "S";
    }
}
