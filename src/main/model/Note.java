package model;

public class Note implements Event {
    public static final int MAX_PITCH = 36;

    private int pitch;

    // REQUIRES: 1 <= pitch <= maxPitch
    // EFFECTS: constructs a legato note with given pitch
    public Note(int pitch) {
        this.pitch = pitch;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    // MODIFIES: this
    // EFFECTS: transpose the note by numSemitones,
    //          if the new pitch is below minPitch or above maxPitch,
    //          keep adding or removing octaves until it is in the proper range
    @Override
    public void transpose(int numSemitones) {
        int newPitch = pitch + numSemitones;
        while (newPitch > MAX_PITCH) {
            newPitch -= 12;
        }
        while (newPitch < 1) {
            newPitch += 12;
        }
        pitch = newPitch;
    }

    // MODIFIES: this
    // EFFECTS: transpose the note up by an octave if it is at least an octave below MAX_PITCH
    @Override
    public void transposeUpByOctave() {
        transpose(12);
    }

    // MODIFIES: this
    // EFFECTS: transpose the note down by an octave if it is at least an octave above 1
    @Override
    public void transposeDownByOctave() {
        transpose(-12);
    }

    // EFFECTS: represents the note as a 6 character string
    @Override
    public String toString() {
        return getLetters() + "-" + getOctave() + " " + getEffect();
    }

    // EFFECTS: returns the letters that represents the note
    private String getLetters() {
        int relPitch = pitch % 12;
        if (relPitch < 6) {
            return getLettersBToE(relPitch);
        } else {
            return getLettersFtoASharp(relPitch);
        }
    }

    private static String getLettersBToE(int relPitch) {
        if (relPitch == 0) {
            return "B-";
        } else if (relPitch == 1) {
            return "C-";
        } else if (relPitch == 2) {
            return "C#";
        } else if (relPitch == 3) {
            return "D-";
        } else if (relPitch == 4) {
            return "D#";
        } else {
            return "E-";
        }
    }

    private static String getLettersFtoASharp(int relPitch) {
        if (relPitch == 6) {
            return "F-";
        } else if (relPitch == 7) {
            return "F#";
        } else if (relPitch == 8) {
            return "G-";
        } else if (relPitch == 9) {
            return "G#";
        } else if (relPitch == 10) {
            return "A-";
        } else {
            return "A#";
        }
    }

    // EFFECTS: returns the octave that the note is part of
    private String getOctave() {
        int octave = ((pitch - 1) / 12) + 1;
        return String.valueOf(octave);
    }

    // EFFECTS: returns the 1 character representation of the effect on the note (if no effect, return "_")
    protected String getEffect() {
        return "_";
    }
}
