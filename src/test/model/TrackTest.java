package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {
    Track track;
    Event event;
    Event e1;
    Event e2;
    Event e3;
    Event e4;
    Event e5;
    Event e6;
    Event e7;
    Event e8;
    Event e9;
    Event e10;

    @BeforeEach
    void setup() {
        track = new Track("track");
        event = new Event();
    }

    @Test
    void testConstructor() {
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
        assertEquals("track", track.getName());
        assertEquals(Track.DEFAULT_BPM, track.getTempo());
        for (int i = 1; i <= InstrumentChannel.INITIAL_NUM_OF_BARS * InstrumentChannel.ROWS_PER_BAR; i++) {
            assertEquals(event, track.getEvent("pulse1", i));
            assertEquals(event, track.getEvent("pulse2", i));
            assertEquals(event, track.getEvent("triangle", i));
            assertEquals(event, track.getEvent("noise", i));
        }
    }

    @Test
    void testSetName() {
        track.setName("song");
        assertEquals("song", track.getName());

        track.setName("piece");
        assertEquals("piece", track.getName());
    }

    @Test
    void testSetTemp() {
        track.setTempo(80);
        assertEquals(80, track.getTempo());

        track.setTempo(110);
        assertEquals(110, track.getTempo());
    }

    @Test
    void testAddBarsOne() {
        track.addBars(1);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 1, track.numberOfBars());
    }

    @Test
    void testAddBarsMany() {
        track.addBars(50);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 50, track.numberOfBars());
    }

    @Test
    void testRemoveBarsOne() {
        track.removeBars(1);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS - 1, track.numberOfBars());
    }

    @Test
    void testRemoveBarsMany() {
        track.addBars(100);
        track.removeBars(50);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 50, track.numberOfBars());
    }

    @Test
    void testNumberOfRows() {
        track.addBars(20);
        int numRows = (InstrumentChannel.INITIAL_NUM_OF_BARS + 20) * InstrumentChannel.ROWS_PER_BAR;
        assertEquals(numRows, track.numberOfRows());

        track.removeBars(7);
        numRows = (InstrumentChannel.INITIAL_NUM_OF_BARS + 13) * InstrumentChannel.ROWS_PER_BAR;
        assertEquals(numRows, track.numberOfRows());
    }

    @Test
    void testNumberOfBars() {
        track.addBars(36);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 36, track.numberOfBars());

        track.removeBars(10);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 26, track.numberOfBars());
    }

    @Test
    void testGetEventBlank() {
        event.clear();
        track.clear("pulse1", 1);
        assertEquals(event, track.getEvent("pulse1", 1));
    }

    @Test
    void testGetEventRest() {
        event.makeRest();
        track.addRest("pulse2", 5);
        assertEquals(event, track.getEvent("pulse2", 5));
    }

    @Test
    void testGetEventNote() {
        event.makeNote(1);
        track.addNote("triangle", 10, 1);
        assertEquals(event, track.getEvent("triangle", 10));
    }

    @Test
    void testAddRest() {
        event.makeRest();
        track.addRest("noise", 12);
        assertEquals(event, track.getEvent("noise", 12));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testAddNote() {
        event.makeNote(10);
        track.addNote("pulse1", 3, 10);
        assertEquals(event, track.getEvent("pulse1", 3));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testAddNoteMultiple() {
        for (int i = 1; i <= 32; i++) {
            track.addNote("pulse1", i, i);
            track.addNote("pulse2", i, i);
            track.addNote("triangle", i, i);
            track.addNote("noise", i, i);
        }
        for (int i = 1; i <= 32; i++) {
            event.makeNote(i);
            assertEquals(event, track.getEvent("pulse1", i));
            assertEquals(event, track.getEvent("pulse2", i));
            assertEquals(event, track.getEvent("triangle", i));
            assertEquals(event, track.getEvent("noise", i));
        }
    }

    @Test
    void testMakeStaccatoBlank() {
        assertFalse(track.makeStaccato("pulse2", 8));
        assertEquals(event, track.getEvent("pulse2", 8));
    }

    @Test
    void testMakeStaccatoRest() {
        event.makeRest();
        track.addRest("triangle", 10);
        assertFalse(track.makeStaccato("triangle", 10));
        assertEquals(event, track.getEvent("triangle", 10));
    }

    @Test
    void testMakeStaccatoNote() {
        event.makeNote(5);
        event.makeStaccato();
        track.addNote("noise", 2, 5);
        assertTrue(track.makeStaccato("noise", 2));
        assertEquals(event, track.getEvent("noise", 2));
    }

    @Test
    void testMakeStaccatoNoteAlreadyStaccato() {
        event.makeNote(4);
        event.makeStaccato();
        track.addNote("pulse1", 16, 4);
        track.makeStaccato("pulse1", 16);
        assertTrue(track.makeStaccato("pulse1", 16));
        assertEquals(event, track.getEvent("pulse1", 16));
    }

    @Test
    void testMakeNotStaccatoBlank() {
        assertFalse(track.makeNotStaccato("pulse2", 14));
        assertEquals(event, track.getEvent("pulse2", 14));
    }

    @Test
    void testMakeNotStaccatoRest() {
        event.makeRest();
        track.addRest("triangle", 16);
        assertFalse(track.makeNotStaccato("triangle", 16));
        assertEquals(event, track.getEvent("triangle", 16));
    }

    @Test
    void testMakeNotStaccatoNoteNotStaccato() {
        event.makeNote(11);
        track.addNote("noise", 3, 11);
        assertTrue(track.makeNotStaccato("noise", 3));
        assertEquals(event, track.getEvent("noise", 3));
    }

    @Test
    void testMakeNotStaccatoNoteStaccato() {
        event.makeNote(20);
        track.addNote("pulse1", 12, 20);
        track.makeStaccato("pulse1", 12);
        assertTrue(track.makeNotStaccato("pulse1", 12));
        assertEquals(event, track.getEvent("pulse1", 12));
    }

    @Test
    void testClearBlank() {
        track.clear("pulse2", 7);
        assertEquals(event, track.getEvent("pulse2", 7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearRest() {
        track.addRest("triangle", 7);
        track.clear("triangle", 7);
        assertEquals(event, track.getEvent("triangle", 7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearNote() {
        track.addNote("noise", 7, 9);
        track.clear("noise", 7);
        assertEquals(event, track.getEvent("noise", 7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearStaccatoNote() {
        track.addNote("pulse1", 7, 9);
        track.makeStaccato("pulse1", 7);
        track.clear("pulse1", 7);
        assertEquals(event, track.getEvent("pulse1", 7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    void setupTrackTemplate() {
        track.addNote("pulse1", 3, Event.MAX_PITCH - 3);
        track.addRest("pulse1", 7);
        track.addNote("pulse1", 10, Event.MAX_PITCH);
        track.addNote("pulse2", 2, 5);
        track.addNote("pulse2", 4, 3);
        track.addNote("triangle", 1, Event.MAX_PITCH - 12);
        track.addRest("triangle", 8);
        track.addNote("noise", 5, 11);
        track.addNote("noise", 6, 1);
        track.addRest("noise", 9);
        e1 = new Event();
        e1.makeNote(Event.MAX_PITCH - 3);
        e2 = new Event();
        e2.makeRest();
        e3 = new Event();
        e3.makeNote(Event.MAX_PITCH);
        e4 = new Event();
        e4.makeNote(5);
        e5 = new Event();
        e5.makeNote(3);
        e6 = new Event();
        e6.makeNote(Event.MAX_PITCH - 12);
        e7 = new Event();
        e7.makeRest();
        e8 = new Event();
        e8.makeNote(11);
        e9 = new Event();
        e9.makeNote(1);
        e10 = new Event();
        e10.makeRest();
    }

    @Test
    void testClearRowsInChannel() {
        setupTrackTemplate();
        track.clear("pulse1", 6, 10);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(event, track.getEvent("pulse1", 7));
        assertEquals(event, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearRows() {
        setupTrackTemplate();
        track.clear(6, 10);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(event, track.getEvent("pulse1", 7));
        assertEquals(event, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(event, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(event, track.getEvent("noise", 6));
        assertEquals(event, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearChannel() {
        setupTrackTemplate();
        track.clear("pulse1");
        assertEquals(event, track.getEvent("pulse1", 3));
        assertEquals(event, track.getEvent("pulse1", 7));
        assertEquals(event, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testClearTrack() {
        setupTrackTemplate();
        track.clear();
        assertEquals(event, track.getEvent("pulse1", 3));
        assertEquals(event, track.getEvent("pulse1", 7));
        assertEquals(event, track.getEvent("pulse1", 10));
        assertEquals(event, track.getEvent("pulse2", 2));
        assertEquals(event, track.getEvent("pulse2", 4));
        assertEquals(event, track.getEvent("triangle", 1));
        assertEquals(event, track.getEvent("triangle", 8));
        assertEquals(event, track.getEvent("noise", 5));
        assertEquals(event, track.getEvent("noise", 6));
        assertEquals(event, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeChannelOne() {
        setupTrackTemplate();
        track.transpose("pulse1", 2);
        e1.makeNote(Event.MAX_PITCH - 1);
        e3.makeNote(Event.MAX_PITCH - 10);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeChannelTwo() {
        setupTrackTemplate();
        track.transpose("noise", -4);
        e8.makeNote(7);
        e9.makeNote(9);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeTrackOne() {
        setupTrackTemplate();
        track.transpose(5);
        e1.makeNote(Event.MAX_PITCH - 10);
        e3.makeNote(Event.MAX_PITCH - 7);
        e4.makeNote(10);
        e5.makeNote(8);
        e6.makeNote(Event.MAX_PITCH - 7);
        e8.makeNote(16);
        e9.makeNote(6);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeTrackTwo() {
        setupTrackTemplate();
        track.transpose(-4);
        e1.makeNote(Event.MAX_PITCH - 7);
        e3.makeNote(Event.MAX_PITCH - 4);
        e4.makeNote(1);
        e5.makeNote(11);
        e6.makeNote(Event.MAX_PITCH - 16);
        e8.makeNote(7);
        e9.makeNote(9);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeUpByOctaveChannel() {
        setupTrackTemplate();
        track.transposeUpByOctave("triangle");
        e6.makeNote(Event.MAX_PITCH);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeUpByOctaveTrack() {
        setupTrackTemplate();
        track.transposeUpByOctave();
        e4.makeNote(17);
        e5.makeNote(15);
        e6.makeNote(Event.MAX_PITCH);
        e8.makeNote(23);
        e9.makeNote(13);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeDownByOctaveChannel() {
        setupTrackTemplate();
        track.transposeDownByOctave("pulse2");
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testTransposeDownByOctaveTrack() {
        setupTrackTemplate();
        track.transposeDownByOctave();
        e1.makeNote(Event.MAX_PITCH - 15);
        e3.makeNote(Event.MAX_PITCH - 12);
        e6.makeNote(Event.MAX_PITCH - 24);
        assertEquals(e1, track.getEvent("pulse1", 3));
        assertEquals(e2, track.getEvent("pulse1", 7));
        assertEquals(e3, track.getEvent("pulse1", 10));
        assertEquals(e4, track.getEvent("pulse2", 2));
        assertEquals(e5, track.getEvent("pulse2", 4));
        assertEquals(e6, track.getEvent("triangle", 1));
        assertEquals(e7, track.getEvent("triangle", 8));
        assertEquals(e8, track.getEvent("noise", 5));
        assertEquals(e9, track.getEvent("noise", 6));
        assertEquals(e10, track.getEvent("noise", 9));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, track.numberOfBars());
    }

    @Test
    void testEqualsDifferentClasses() {
        assertNotEquals(track, new Object());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(track, null);
    }

    @Test
    void testEqualsPulse1Different() {
        Track track1 = new Track("track");
        Track track2 = new Track("track");
        track2.addRest("pulse1", 1);
        assertNotEquals(track1, track2);
    }

    @Test
    void testEqualsPulse2Different() {
        Track track1 = new Track("track");
        Track track2 = new Track("track");
        track2.addRest("pulse2", 1);
        assertNotEquals(track1, track2);
    }

    @Test
    void testEqualsTriangleDifferent() {
        Track track1 = new Track("track");
        Track track2 = new Track("track");
        track2.addRest("triangle", 1);
        assertNotEquals(track1, track2);
    }

    @Test
    void testEqualsNoiseDifferent() {
        Track track1 = new Track("track");
        Track track2 = new Track("track");
        track2.addRest("noise", 1);
        assertNotEquals(track1, track2);
    }

    @Test
    void testEqualsDifferentTempos() {
        Track track1 = new Track("track");
        Track track2 = new Track("track");
        track1.setTempo(100);
        track2.setTempo(140);
        assertNotEquals(track1, track2);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(track, track);
        assertEquals(track.hashCode(), track.hashCode());
    }
}