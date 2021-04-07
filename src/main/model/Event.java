package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents an event that can be placed inside a track
// Can be a note, a rest, or a blank event
// Notes have a pitch and can be staccato or not staccato
public class Event implements Writable {
    public static final int NUM_OCTAVES = 5;
    public static final int MAX_PITCH = NUM_OCTAVES * 12;

    private String type;
    private int pitch;
    private boolean isStaccato;

    // EFFECTS: constructs a blank event
    public Event() {
        type = "blank";
        pitch = 0;
        isStaccato = false;
    }

    public String getType() {
        return type;
    }

    public int getPitch() {
        return pitch;
    }

    public boolean getIsStaccato() {
        return isStaccato;
    }

    // REQUIRES: 1 <= pitch <= MAX_PITCH
    // MODIFIES: this
    // EFFECTS: converts the event into a non-staccato note with the given pitch
    public void makeNote(int pitch) {
        type = "note";
        this.pitch = pitch;
        isStaccato = false;
    }

    // MODIFIES: this
    // EFFECTS: converts the event into a rest
    public void makeRest() {
        type = "rest";
        pitch = 0;
        isStaccato = false;
    }

    // MODIFIES: this
    // EFFECTS: converts the event into a blank
    public void clear() {
        type = "blank";
        pitch = 0;
        isStaccato = false;
    }

    // MODIFIES: this
    // EFFECTS: if the event is a note, make it staccato and return true, otherwise return false
    public boolean makeStaccato() {
        if (type.equals("note")) {
            isStaccato = true;
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the event is a note, make it not staccato and return true, otherwise return false
    public boolean makeNotStaccato() {
        if (type.equals("note")) {
            isStaccato = false;
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the event is a note: transpose by numSemitones,
    //          if the new pitch is below minPitch or above maxPitch,
    //          keep adding or removing octaves until it is in the proper range
    //          if the event is not a note, do nothing
    public void transpose(int numSemitones) {
        if (type.equals("note")) {
            int newPitch = pitch + numSemitones;
            while (newPitch > MAX_PITCH) {
                newPitch -= 12;
            }
            while (newPitch < 1) {
                newPitch += 12;
            }
            pitch = newPitch;
        }
    }

    // MODIFIES: this
    // EFFECTS: transpose the event up by an octave if it is a note and at least an octave below MAX_PITCH
    public void transposeUpByOctave() {
        transpose(12);
    }

    // MODIFIES: this
    // EFFECTS: transpose the event down by an octave if it is a note and at least an octave above 1
    public void transposeDownByOctave() {
        transpose(-12);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return pitch == event.pitch
                && isStaccato == event.isStaccato
                && type.equals(event.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pitch, isStaccato);
    }

    // EFFECTS: represents the note as a 6 character string
    @Override
    public String toString() {
        if (type.equals("note")) {
            return getLetters() + "-" + getOctave() + " " + getEffect();
        } else if (type.equals("rest")) {
            return "STOP .";
        } else {
            return ".... .";
        }
    }

    // EFFECTS: returns this as JSON object
    // (modelled after JsonSerializationDemo repository)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("pitch", pitch);
        json.put("isStaccato", isStaccato);
        return json;
    }

    // REQUIRES: the event is of type "note"
    // EFFECTS: returns the letters that represents the event
    private String getLetters() {
        int relPitch = pitch % 12;
        if (relPitch < 6) {
            return getLettersBToE(relPitch);
        } else {
            return getLettersFtoASharp(relPitch);
        }
    }

    // REQUIRES: 0 <= relPitch <= 5
    // EFFECTS: returns the letters that represent the given relative pitch
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

    // REQUIRES: 6 <= relPitch <= 11
    // EFFECTS: returns the letters that represent the given relative pitch
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

    // REQUIRES: the event is a note
    // EFFECTS: returns the octave that the event is part of
    private String getOctave() {
        int octave = ((pitch - 1) / 12) + 1;
        return String.valueOf(octave);
    }

    // REQUIRES: the event is a note
    // EFFECTS: returns the 1 character representation of the effect on the event (if no effect, return "_")
    private String getEffect() {
        if (isStaccato) {
            return "S";
        } else {
            return ".";
        }
    }
}
