package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackerTest {
    Tracker tracker;
    Track track1;
    Track track2;
    Track track3;

    @BeforeEach
    void setup() {
        tracker = new Tracker();
        track1 = new Track("track1");
        track2 = new Track("track2");
        track3 = new Track("track3");
    }

    @Test
    void testConstructor() {
        assertEquals(0, tracker.numberOfTracks());
        assertFalse(tracker.contains(track1));
        assertFalse(tracker.contains(track2));
        assertFalse(tracker.contains(track3));
    }

    @Test
    void testAddOne() {
        tracker.add(track1);
        assertEquals(1, tracker.numberOfTracks());
        assertTrue(tracker.contains(track1));
        assertFalse(tracker.contains(track2));
        assertFalse(tracker.contains(track3));
    }

    @Test
    void testAddTwo() {
        tracker.add(track1);
        tracker.add(track2);
        assertEquals(2, tracker.numberOfTracks());
        assertTrue(tracker.contains(track1));
        assertTrue(tracker.contains(track2));
        assertFalse(tracker.contains(track3));
    }

    @Test
    void testAddThree() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        assertEquals(3, tracker.numberOfTracks());
        assertTrue(tracker.contains(track1));
        assertTrue(tracker.contains(track2));
        assertTrue(tracker.contains(track3));
    }

    @Test
    void testRemoveOne() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        tracker.remove(1);
        assertEquals(2, tracker.numberOfTracks());
        assertFalse(tracker.contains(track1));
        assertTrue(tracker.contains(track2));
        assertTrue(tracker.contains(track3));
    }

    @Test
    void testRemoveTwo() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        tracker.remove(1);
        tracker.remove(2);
        assertEquals(1, tracker.numberOfTracks());
        assertFalse(tracker.contains(track1));
        assertTrue(tracker.contains(track2));
        assertFalse(tracker.contains(track3));
    }

    @Test
    void testRemoveThree() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        tracker.remove(3);
        tracker.remove(2);
        tracker.remove(1);
        assertEquals(0, tracker.numberOfTracks());
        assertFalse(tracker.contains(track1));
        assertFalse(tracker.contains(track2));
        assertFalse(tracker.contains(track3));
    }

    @Test
    void testGetPositionEmpty() {
        assertNull(tracker.get(1));
        assertNull(tracker.get(2));
        assertNull(tracker.get(3));
    }

    @Test
    void testGetPositionOne() {
        tracker.add(track1);
        assertEquals(track1, tracker.get(1));
        assertNull(tracker.get(2));
        assertNull(tracker.get(3));
    }

    @Test
    void testGetPositionThree() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        assertEquals(track1, tracker.get(1));
        assertEquals(track2, tracker.get(2));
        assertEquals(track3, tracker.get(3));
    }

    @Test
    void testGetPositionInvalid() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        assertNull(tracker.get(0));
        assertNull(tracker.get(4));
    }

    @Test
    void testGetNameEmpty() {
        assertNull(tracker.get("track1"));
        assertNull(tracker.get("track2"));
        assertNull(tracker.get("track3"));
    }

    @Test
    void testGetNameOne() {
        tracker.add(track1);
        assertEquals(track1, tracker.get("track1"));
        assertNull(tracker.get("track2"));
        assertNull(tracker.get("track3"));
    }

    @Test
    void testGetNameThree() {
        tracker.add(track1);
        tracker.add(track2);
        tracker.add(track3);
        assertEquals(track1, tracker.get("track1"));
        assertEquals(track2, tracker.get("track2"));
        assertEquals(track3, tracker.get("track3"));
    }
}
