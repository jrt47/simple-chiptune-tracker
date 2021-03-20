package persistence;

import model.Event;
import model.Track;
import model.Tracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// modelled after JsonSerializationDemo repository
public class JsonWriterTest {
    Tracker tracker;

    @BeforeEach
    void setup() {
        tracker = new Tracker();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTracker() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTracker.json");
            tracker = reader.read();
            assertEquals(0, tracker.numberOfTracks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void setupGeneralTracks(Track track1, Track track2) {
        track1.setTempo(100);
        track2.addBars(1);
        track1.addNote("pulse1", 3, Event.MAX_PITCH - 3);
        track1.makeStaccato("pulse1", 3);
        track2.addRest("pulse1", 7);
        track1.addNote("pulse1", 10, Event.MAX_PITCH);
        track2.addNote("pulse2", 2, 5);
        track2.makeStaccato("pulse2", 3);
        track1.addNote("pulse2", 4, 3);
        track2.addNote("triangle", 1, Event.MAX_PITCH - 12);
        track1.addRest("triangle", 8);
        track2.addNote("noise", 5, 11);
        track2.makeStaccato("noise", 5);
        track1.addNote("noise", 6, 1);
        track2.addRest("noise", 9);
    }

    @Test
    void testWriterGeneralTracker() {
        try {
            Track track1 = new Track("track1");
            Track track2 = new Track ("track2");
            setupGeneralTracks(track1, track2);
            tracker.add(track1);
            tracker.add(track2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTracker.json");
            tracker = reader.read();
            assertEquals(2, tracker.numberOfTracks());
            Track testTrack1 = new Track("track1");
            Track testTrack2 = new Track("track2");
            setupGeneralTracks(testTrack1, testTrack2);
            assertEquals(testTrack1, tracker.get(1));
            assertEquals(testTrack2, tracker.get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
