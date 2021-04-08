package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents an instrument channel inside of a track
// Instrument channels have a set amount of bars and can have notes or rests placed in them
public class InstrumentChannel implements Writable {
    public static final int INITIAL_NUM_OF_BARS = 2;
    public static final int ROWS_PER_BAR = 16;

    List<Event> eventList;

    // EFFECTS: constructs an empty 2 bar instrument channel
    public InstrumentChannel() {
        eventList = new ArrayList<>(ROWS_PER_BAR * 2);
        for (int i = 1; i <= ROWS_PER_BAR * 2; i++) {
            eventList.add(new Event());
        }
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: adds numBars bars to the channel
    public void addBars(int numBars) {
        for (int i = 1; i <= ROWS_PER_BAR * numBars; i++) {
            eventList.add(new Event());
        }
    }

    // REQUIRES: 0 < numBars < numberOfBars()
    // MODIFIES: this
    // EFFECTS: removes numBars bars from the channel
    public void removeBars(int numBars) {
        int numRowsToClear = numBars * ROWS_PER_BAR;
        for (int i = 1; i <= numRowsToClear; i++) {
            eventList.remove(numberOfRows() - 1);
        }
    }

    // EFFECTS: returns the number of rows in the channel
    public int numberOfRows() {
        return eventList.size();
    }

    // EFFECTS: returns the number of bars in the channel
    public int numberOfBars() {
        return eventList.size() / ROWS_PER_BAR;
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // EFFECTS: returns the event at the given row
    public Event getEvent(int row) {
        return eventList.get(row - 1);
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a note with given pitch at given row
    public void addNote(int row, int pitch) {
        eventList.get(row - 1).makeNote(pitch);
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a rest at given row
    public void addRest(int row) {
        eventList.get(row - 1).makeRest();
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: if event at given row is a note, make it staccato and return true, otherwise return false
    public boolean makeStaccato(int row) {
        return eventList.get(row - 1).makeStaccato();
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: if event at given row is a note, make it not staccato and return true, otherwise return false
    public boolean makeNotStaccato(int row) {
        return eventList.get(row - 1).makeNotStaccato();
    }

    // REQUIRES: 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: clears the event at the given row
    public void clear(int row) {
        eventList.get(row - 1).clear();
    }

    // REQUIRES: 1 <= startRow/endRow <= numberOfRows(), endRow >= startRow
    // MODIFIES: this
    // EFFECTS: clears all events in the channel between startRow and endRow (inclusive)
    public void clear(int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
            clear(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all events in the channel
    public void clear() {
        for (Event event : eventList) {
            event.clear();
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the channel by numSemitones semitones
    public void transpose(int numSemitones) {
        for (Event event : eventList) {
            event.transpose(numSemitones);
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the channel up by an octave
    public void transposeUpByOctave() {
        for (Event event : eventList) {
            event.transposeUpByOctave();
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the channel down by an octave
    public void transposeDownByOctave() {
        for (Event event : eventList) {
            event.transposeDownByOctave();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstrumentChannel that = (InstrumentChannel) o;
        return eventList.equals(that.eventList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventList);
    }

    // EFFECTS: returns this as JSON object
    // (modelled after JsonSerializationDemo repository)
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("eventList", eventListToJson());
        return json;
    }

    // EFFECTS: returns events in this channel as a JSON array
    // (modelled after JsonSerializationDemo repository)
    private JSONArray eventListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event event : eventList) {
            jsonArray.put(event.toJson());
        }
        return jsonArray;
    }
}
