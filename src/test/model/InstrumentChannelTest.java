package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstrumentChannelTest {
    InstrumentChannel channel;
    Event event;

    @BeforeEach
    void setup() {
        channel = new InstrumentChannel();
        event = new Event();
    }

    @Test
    void testConstructor() {
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
        for (int i = 1; i <= InstrumentChannel.INITIAL_NUM_OF_BARS * InstrumentChannel.ROWS_PER_BAR; i++) {
            assertEquals(event, channel.getEvent(i));
        }
    }

    @Test
    void testAddBarsOne() {
        channel.addBars(1);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 1, channel.numberOfBars());
    }

    @Test
    void testAddBarsMany() {
        channel.addBars(50);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 50, channel.numberOfBars());
    }

    @Test
    void testRemoveBarsOne() {
        channel.removeBars(1);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS - 1, channel.numberOfBars());
    }

    @Test
    void testRemoveBarsMany() {
        channel.addBars(100);
        channel.removeBars(50);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 50, channel.numberOfBars());
    }

    @Test
    void testNumberOfRows() {
        channel.addBars(20);
        int numRows = (InstrumentChannel.INITIAL_NUM_OF_BARS + 20) * InstrumentChannel.ROWS_PER_BAR;
        assertEquals(numRows, channel.numberOfRows());

        channel.removeBars(7);
        numRows = (InstrumentChannel.INITIAL_NUM_OF_BARS + 13) * InstrumentChannel.ROWS_PER_BAR;
        assertEquals(numRows, channel.numberOfRows());
    }

    @Test
    void testNumberOfBars() {
        channel.addBars(36);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 36, channel.numberOfBars());

        channel.removeBars(10);
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS + 26, channel.numberOfBars());
    }

    @Test
    void testGetEventBlank() {
        event.clear();
        channel.clear(1);
        assertEquals(event, channel.getEvent(1));
    }

    @Test
    void testGetEventRest() {
        event.makeRest();
        channel.addRest(5);
        assertEquals(event, channel.getEvent(5));
    }

    @Test
    void testGetEventNote() {
        event.makeNote(1);
        channel.addNote(10, 1);
        assertEquals(event, channel.getEvent(10));
    }

    @Test
    void testAddRest() {
        event.makeRest();
        channel.addRest(12);
        assertEquals(event, channel.getEvent(12));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testAddNote() {
        event.makeNote(10);
        channel.addNote(3, 10);
        assertEquals(event, channel.getEvent(3));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testAddNoteMultiple() {
        for (int i = 1; i <= 32; i++) {
            channel.addNote(i, i);
        }
        for (int i = 1; i <= 32; i++) {
            event.makeNote(i);
            assertEquals(event, channel.getEvent(i));
        }
    }

    @Test
    void testMakeStaccatoBlank() {
        assertFalse(channel.makeStaccato(8));
        assertEquals(event, channel.getEvent(8));
    }

    @Test
    void testMakeStaccatoRest() {
        event.makeRest();
        channel.addRest(10);
        assertFalse(channel.makeStaccato(10));
        assertEquals(event, channel.getEvent(10));
    }

    @Test
    void testMakeStaccatoNote() {
        event.makeNote(5);
        event.makeStaccato();
        channel.addNote(2, 5);
        assertTrue(channel.makeStaccato(2));
        assertEquals(event, channel.getEvent(2));
    }

    @Test
    void testMakeStaccatoAlreadyStaccato() {
        event.makeNote(4);
        event.makeStaccato();
        channel.addNote(16, 4);
        channel.makeStaccato(16);
        assertTrue(channel.makeStaccato(16));
        assertEquals(event, channel.getEvent(16));
    }

    @Test
    void testMakeNotStaccatoBlank() {
        assertFalse(channel.makeNotStaccato(14));
        assertEquals(event, channel.getEvent(14));
    }

    @Test
    void testMakeNotStaccatoRest() {
        event.makeRest();
        channel.addRest(16);
        assertFalse(channel.makeNotStaccato(16));
        assertEquals(event, channel.getEvent(16));
    }

    @Test
    void testMakeNotStaccatoNoteNotStaccato() {
        event.makeNote(11);
        channel.addNote(3, 11);
        assertTrue(channel.makeNotStaccato(3));
        assertEquals(event, channel.getEvent(3));
    }

    @Test
    void testMakeNotStaccatoNoteStaccato() {
        event.makeNote(20);
        channel.addNote(12, 20);
        channel.makeStaccato(12);
        assertTrue(channel.makeNotStaccato(12));
        assertEquals(event, channel.getEvent(12));
    }

    @Test
    void testClearBlank() {
        channel.clear(7);
        assertEquals(event, channel.getEvent(7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearRest() {
        channel.addRest(7);
        channel.clear(7);
        assertEquals(event, channel.getEvent(7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearNote() {
        channel.addNote(7, 9);
        channel.clear(7);
        assertEquals(event, channel.getEvent(7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearStaccatoNote() {
        channel.addNote(7, 9);
        channel.makeStaccato(7);
        channel.clear(7);
        assertEquals(event, channel.getEvent(7));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearRowsOne() {
        channel.addRest(1);
        channel.addNote(2, 11);
        channel.addRest(3);
        channel.addNote(4, 3);
        channel.addRest(5);
        channel.clear(3, 5);
        Event rest = new Event();
        rest.makeRest();
        Event note = new Event();
        note.makeNote(11);
        assertEquals(rest, channel.getEvent(1));
        assertEquals(note, channel.getEvent(2));
        assertEquals(event, channel.getEvent(3));
        assertEquals(event, channel.getEvent(4));
        assertEquals(event, channel.getEvent(5));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearRowsTwo() {
        channel.addNote(1, 5);
        channel.addNote(4, 4);
        channel.addRest(8);
        channel.addNote(9, 6);
        channel.addNote(10, 10);
        channel.addNote(12, 19);
        channel.addRest(18);
        channel.clear(1, 7);
        channel.clear(11, 20);
        Event rest = new Event();
        rest.makeRest();
        Event note1 = new Event();
        note1.makeNote(6);
        Event note2 = new Event();
        note2.makeNote(10);
        assertEquals(event, channel.getEvent(1));
        assertEquals(event, channel.getEvent(4));
        assertEquals(rest, channel.getEvent(8));
        assertEquals(note1, channel.getEvent(9));
        assertEquals(note2, channel.getEvent(10));
        assertEquals(event, channel.getEvent(12));
        assertEquals(event, channel.getEvent(18));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testClearAll() {
        channel.addNote(2, 10);
        channel.addNote(4, 11);
        channel.addNote(5, 13);
        channel.addRest(10);
        channel.addNote(14, 10);
        channel.addRest(16);
        channel.clear();
        assertEquals(event, channel.getEvent(2));
        assertEquals(event, channel.getEvent(4));
        assertEquals(event, channel.getEvent(5));
        assertEquals(event, channel.getEvent(10));
        assertEquals(event, channel.getEvent(14));
        assertEquals(event, channel.getEvent(16));
        assertEquals(InstrumentChannel.INITIAL_NUM_OF_BARS, channel.numberOfBars());
    }

    @Test
    void testTransposeOne() {
        channel.addNote(1, 5);
        channel.addNote(3, 10);
        channel.addRest(4);
        channel.addNote(8, Event.MAX_PITCH - 2);
        channel.addRest(10);
        channel.addNote(11, Event.MAX_PITCH - 6);
        channel.addNote(14, 7);
        channel.addNote(17, Event.MAX_PITCH);
        channel.transpose(4);
        Event e1 = new Event();
        e1.makeNote(9);
        Event e3 = new Event();
        e3.makeNote(14);
        Event e4 = new Event();
        e4.makeRest();
        Event e8 = new Event();
        e8.makeNote(Event.MAX_PITCH - 10);
        Event e10 = new Event();
        e10.makeRest();
        Event e11 = new Event();
        e11.makeNote(Event.MAX_PITCH - 2);
        Event e14 = new Event();
        e14.makeNote(11);
        Event e17 = new Event();
        e17.makeNote(Event.MAX_PITCH - 8);
        assertEquals(e1, channel.getEvent(1));
        assertEquals(e3, channel.getEvent(3));
        assertEquals(e4, channel.getEvent(4));
        assertEquals(e8, channel.getEvent(8));
        assertEquals(e10, channel.getEvent(10));
        assertEquals(e11, channel.getEvent(11));
        assertEquals(e14, channel.getEvent(14));
        assertEquals(e17, channel.getEvent(17));
    }

    @Test
    void testTransposeTwo() {
        channel.addNote(1, 7);
        channel.addRest(2);
        channel.addNote(5, Event.MAX_PITCH);
        channel.addNote(8, 3);
        channel.addNote(10, Event.MAX_PITCH - 4);
        channel.addRest(13);
        channel.addNote(15, 9);
        channel.addRest(16);
        channel.transpose(-6);
        Event e1 = new Event();
        e1.makeNote(1);
        Event e2 = new Event();
        e2.makeRest();
        Event e5 = new Event();
        e5.makeNote(Event.MAX_PITCH - 6);
        Event e8 = new Event();
        e8.makeNote(9);
        Event e10 = new Event();
        e10.makeNote(Event.MAX_PITCH - 10);
        Event e13 = new Event();
        e13.makeRest();
        Event e15 = new Event();
        e15.makeNote(3);
        Event e16 = new Event();
        e16.makeRest();
        assertEquals(e1, channel.getEvent(1));
        assertEquals(e2, channel.getEvent(2));
        assertEquals(e5, channel.getEvent(5));
        assertEquals(e8, channel.getEvent(8));
        assertEquals(e10, channel.getEvent(10));
        assertEquals(e13, channel.getEvent(13));
        assertEquals(e15, channel.getEvent(15));
        assertEquals(e16, channel.getEvent(16));
    }

    @Test
    void testTransposeUpByOctave() {
        channel.addNote(1, Event.MAX_PITCH);
        channel.addNote(3, 4);
        channel.addRest(4);
        channel.addNote(8, Event.MAX_PITCH - 12);
        channel.addRest(10);
        channel.addNote(11, 1);
        channel.addNote(14, 3);
        channel.addNote(17, Event.MAX_PITCH - 6);
        channel.transposeUpByOctave();
        Event e1 = new Event();
        e1.makeNote(Event.MAX_PITCH);
        Event e3 = new Event();
        e3.makeNote(16);
        Event e4 = new Event();
        e4.makeRest();
        Event e8 = new Event();
        e8.makeNote(Event.MAX_PITCH);
        Event e10 = new Event();
        e10.makeRest();
        Event e11 = new Event();
        e11.makeNote(13);
        Event e14 = new Event();
        e14.makeNote(15);
        Event e17 = new Event();
        e17.makeNote(Event.MAX_PITCH - 6);
        assertEquals(e1, channel.getEvent(1));
        assertEquals(e3, channel.getEvent(3));
        assertEquals(e4, channel.getEvent(4));
        assertEquals(e8, channel.getEvent(8));
        assertEquals(e10, channel.getEvent(10));
        assertEquals(e11, channel.getEvent(11));
        assertEquals(e14, channel.getEvent(14));
        assertEquals(e17, channel.getEvent(17));
    }

    @Test
    void testTransposeDownByOctave() {
        channel.addNote(1, 5);
        channel.addRest(2);
        channel.addNote(5, Event.MAX_PITCH);
        channel.addNote(8, 13);
        channel.addNote(10, 8);
        channel.addRest(13);
        channel.addNote(15, Event.MAX_PITCH - 3);
        channel.addRest(16);
        channel.transposeDownByOctave();
        Event e1 = new Event();
        e1.makeNote(5);
        Event e2 = new Event();
        e2.makeRest();
        Event e5 = new Event();
        e5.makeNote(Event.MAX_PITCH - 12);
        Event e8 = new Event();
        e8.makeNote(1);
        Event e10 = new Event();
        e10.makeNote(8);
        Event e13 = new Event();
        e13.makeRest();
        Event e15 = new Event();
        e15.makeNote(Event.MAX_PITCH - 15);
        Event e16 = new Event();
        e16.makeRest();
        assertEquals(e1, channel.getEvent(1));
        assertEquals(e2, channel.getEvent(2));
        assertEquals(e5, channel.getEvent(5));
        assertEquals(e8, channel.getEvent(8));
        assertEquals(e10, channel.getEvent(10));
        assertEquals(e13, channel.getEvent(13));
        assertEquals(e15, channel.getEvent(15));
        assertEquals(e16, channel.getEvent(16));
    }

    void setupChannel1(InstrumentChannel channel) {
        channel.addNote(3, Event.MAX_PITCH - 3);
        channel.makeStaccato(3);
        channel.addNote(10, Event.MAX_PITCH);
        channel.addNote(4, 3);
        channel.addRest(8);
        channel.addNote(6, 1);
    }

    void setupChannel2(InstrumentChannel channel) {
        channel.addRest(7);
        channel.addNote(2, 5);
        channel.makeStaccato(3);
        channel.addNote(1, Event.MAX_PITCH - 12);
        channel.addNote(5, 11);
        channel.makeStaccato(5);
        channel.addRest(9);
    }

    @Test
    void testEqualsNotIdentical() {
        InstrumentChannel channel1 = new InstrumentChannel();
        InstrumentChannel channel2 = new InstrumentChannel();
        setupChannel1(channel1);
        setupChannel2(channel2);
        assertNotEquals(channel2, channel1);
        assertNotEquals(channel1, channel2);
    }

    @Test
    void testEqualsDifferentLengths() {
        InstrumentChannel channel1 = new InstrumentChannel();
        InstrumentChannel channel2 = new InstrumentChannel();
        setupChannel1(channel1);
        setupChannel2(channel2);
        channel2.addBars(1);
        assertNotEquals(channel2, channel1);
        assertNotEquals(channel1, channel2);
    }

    @Test
    void testEqualsIdentical() {
        InstrumentChannel channel1 = new InstrumentChannel();
        InstrumentChannel channel2 = new InstrumentChannel();
        setupChannel1(channel1);
        setupChannel1(channel2);
        assertEquals(channel2, channel1);
        assertEquals(channel1, channel2);
        assertEquals(channel1.hashCode(), channel2.hashCode());
    }

    @Test
    void testEqualsDifferentClasses() {
        assertNotEquals(channel, new Object());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(channel, null);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(channel, channel);
        assertEquals(channel.hashCode(), channel.hashCode());
    }
}
