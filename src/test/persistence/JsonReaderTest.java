package persistence;

import model.Track;
import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

// modelled after JsonSerializationDemo repository
public class JsonReaderTest {
    Tracker tracker = new Tracker();

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            tracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTracker.json");
        try {
            tracker = reader.read();
            assertEquals(0, tracker.numberOfTracks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    void setupGeneralTracks(Track track1, Track track2) {
        track1.removeBars(1);
        track1.addNote("pulse1", 1, 1);
        track1.addNote("pulse2", 3, 10);
        track1.addRest("pulse2", 1);
        track1.addNote("triangle", 14, 7);
        track1.addNote("noise", 8, 11);
        track2.setTempo(150);
        track2.addNote("pulse1", 3, 6);
        track2.addNote("pulse2", 1, 9);
        track2.addNote("triangle", 20, 17);
        track2.addNote("noise", 7, 13);
        track2.addRest("noise", 15);
    }

    @Test
    void testReaderGeneralTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTracker.json");
        try {
            tracker = reader.read();
            assertEquals(2, tracker.numberOfTracks());
            Track testTrack1 = new Track("track1");
            Track testTrack2 = new Track("track2");
            setupGeneralTracks(testTrack1, testTrack2);
            assertEquals(testTrack1, tracker.get(1));
            assertEquals(testTrack2, tracker.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
