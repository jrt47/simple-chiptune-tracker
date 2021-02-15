package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    Event event;

    @BeforeEach
    void setup() {
        event = new Event();
    }

    @Test
    void testConstructor() {
        assertEquals("blank", event.getType());
        assertEquals(0, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeNoteBlank() {
        event.clear();
        event.makeNote(5);
        assertEquals("note", event.getType());
        assertEquals(5, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeNoteRest() {
        event.makeRest();
        event.makeNote(10);
        assertEquals("note", event.getType());
        assertEquals(10, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeRestBlank() {
        event.clear();
        event.makeRest();
        assertEquals("rest", event.getType());
        assertEquals(0, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeRestNote() {
        event.makeNote(8);
        event.makeRest();
        assertEquals("rest", event.getType());
        assertEquals(0, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testClearNote() {
        event.makeNote(4);
        event.clear();
        assertEquals("blank", event.getType());
        assertEquals(0, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testClearRest() {
        event.makeRest();
        event.clear();
        assertEquals("blank", event.getType());
        assertEquals(0, event.getPitch());
        assertFalse(event.getIsStaccato());
    }

    void testTranspose(int pitch, int numSemitones, int expected) {
        event.makeNote(pitch);
        event.transpose(numSemitones);
        assertEquals(expected, event.getPitch());
    }

    @Test
    void testMakeStaccatoBlank() {
        event.clear();
        assertFalse(event.makeStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeStaccatoRest() {
        event.makeRest();
        assertFalse(event.makeStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeStaccatoNote() {
        event.makeNote(12);
        assertTrue(event.makeStaccato());
        assertTrue(event.getIsStaccato());
    }

    @Test
    void testMakeStaccatoNoteAlreadyStaccato() {
        event.makeNote(12);
        event.makeStaccato();
        assertTrue(event.makeStaccato());
        assertTrue(event.getIsStaccato());
    }

    @Test
    void testMakeNotStaccatoBlank() {
        event.clear();
        assertFalse(event.makeNotStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeNotStaccatoRest() {
        event.makeRest();
        assertFalse(event.makeNotStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeNotStaccatoNote() {
        event.makeNote(3);
        event.makeStaccato();
        assertTrue(event.makeNotStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testMakeNotStaccatoNoteAlreadyNotStaccato() {
        event.makeNote(1);
        assertTrue(event.makeNotStaccato());
        assertFalse(event.getIsStaccato());
    }

    @Test
    void testTransposeUpWithinRange() {
        testTranspose(7, 4, 11);
    }

    @Test
    void testTransposeDownWithinRange() {
        testTranspose(12, -3, 9);
    }

    @Test
    void testTransposeUpJustWithinRange() {
        testTranspose(Event.MAX_PITCH - 5, 5, Event.MAX_PITCH);
    }

    @Test
    void testTransposeDownJustWithinRange() {
        testTranspose(7, -6, 1);
    }

    @Test
    void testTransposeUpJustOutOfRange() {
        testTranspose(Event.MAX_PITCH - 3, 4, Event.MAX_PITCH - 11);
    }

    @Test
    void testTransposeDownJustOutOfRange() {
        testTranspose(4, -5, 11);
    }

    @Test
    void testTransposeUpOutOfRange() {
        testTranspose(Event.MAX_PITCH - 2, 7, Event.MAX_PITCH - 7);
    }

    @Test
    void testTransposeDownOutOfRange() {
        testTranspose(2, -5, 9);
    }

    @Test
    void testTransposeBlank() {
        event.clear();
        event.transpose(5);
        assertEquals(0, event.getPitch());
    }

    @Test
    void testTransposeRest() {
        event.makeRest();
        event.transpose(-5);
        assertEquals(0, event.getPitch());
    }

    void testTransposeUpByOctave(int pitch, int expected) {
        event.makeNote(pitch);
        event.transposeUpByOctave();
        assertEquals(expected, event.getPitch());
    }

    @Test
    void testTransposeUpByOctaveWithinRange() {
        testTransposeUpByOctave(4, 16);
    }

    @Test
    void testTransposeUpByOctaveJustWithinRange() {
        testTransposeUpByOctave(Event.MAX_PITCH - 12, Event.MAX_PITCH);
    }

    @Test
    void testTransposeUpByOctaveJustOutOfRange() {
        testTransposeUpByOctave(Event.MAX_PITCH - 11, Event.MAX_PITCH - 11);
    }

    @Test
    void testTransposeUpByOctaveOutOfRange() {
        testTransposeUpByOctave(Event.MAX_PITCH - 7, Event.MAX_PITCH - 7);
    }

    void testTransposeDownByOctave(int pitch, int expected) {
        event.makeNote(pitch);
        event.transposeDownByOctave();
        assertEquals(expected, event.getPitch());
    }

    @Test
    void testTransposeDownByOctaveWithinRange() {
        testTransposeDownByOctave(15, 3);
    }

    @Test
    void testTransposeDownByOctaveJustWithinRange() {
        testTransposeDownByOctave(13, 1);
    }

    @Test
    void testTransposeDownByOctaveJustOutOfRange() {
        testTransposeDownByOctave(12, 12);
    }

    @Test
    void testTransposeDownByOctaveOutOfRange() {
        testTransposeDownByOctave(5, 5);
    }

    @Test
    void testIsIdenticalToNotesSamePitch() {
        Event note1 = new Event();
        note1.makeNote(14);
        Event note2 = new Event();
        note2.makeNote(14);
        assertTrue(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdenticalToNotesSamePitchOneStaccato() {
        Event note1 = new Event();
        note1.makeNote(6);
        Event note2 = new Event();
        note2.makeNote(6);
        note2.makeStaccato();
        assertFalse(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdenticalToNotesSamePitchBothStaccato() {
        Event note1 = new Event();
        note1.makeNote(1);
        note1.makeStaccato();
        Event note2 = new Event();
        note2.makeNote(1);
        note2.makeStaccato();
        assertTrue(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdenticalToNotesDifferentPitch() {
        Event note1 = new Event();
        note1.makeNote(12);
        Event note2 = new Event();
        note2.makeNote(20);
        assertFalse(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdenticalToNotesDifferentPitchOneStaccato() {
        Event note1 = new Event();
        note1.makeNote(3);
        Event note2 = new Event();
        note2.makeNote(1);
        note2.makeStaccato();
        assertFalse(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdentiaclToNotesDifferentPitchBothStaccato() {
        Event note1 = new Event();
        note1.makeNote(18);
        note1.makeStaccato();
        Event note2 = new Event();
        note2.makeNote(19);
        note2.makeStaccato();
        assertFalse(note1.isIdenticalTo(note2));
    }

    @Test
    void testIsIdenticalToRests() {
        Event rest1 = new Event();
        rest1.makeRest();
        Event rest2 = new Event();
        rest2.makeRest();
        assertTrue(rest1.isIdenticalTo(rest2));
    }

    @Test
    void testIsIdenticalToBlanks() {
        Event blank1 = new Event();
        Event blank2 = new Event();
        assertTrue(blank1.isIdenticalTo(blank2));
    }

    @Test
    void testIsIdenticalToNoteAndRest() {
        Event note = new Event();
        note.makeNote(5);
        Event rest = new Event();
        rest.makeRest();
        assertFalse(note.isIdenticalTo(rest));
    }

    @Test
    void testIsIdenticalToNoteAndBlank() {
        Event note = new Event();
        note.makeNote(5);
        Event blank = new Event();
        assertFalse(note.isIdenticalTo(blank));
    }

    @Test
    void testIsIdenticalToRestAndBlank() {
        Event rest = new Event();
        rest.makeRest();
        Event blank = new Event();
        assertFalse(rest.isIdenticalTo(blank));
    }

    void testToString(int pitch, boolean isStaccato, String expected) {
        event.makeNote(pitch);
        if (isStaccato) {
            event.makeStaccato();
        }
        assertEquals(expected, event.toString());
    }

    @Test
    void testToStringNote() {
        testToString(1, false, "C--1 _");
        testToString(14, true, "C#-2 S");
        testToString(27, false, "D--3 _");
        testToString(4, true, "D#-1 S");
        testToString(17, false, "E--2 _");
        testToString(30, true, "F--3 S");
        testToString(7, false, "F#-1 _");
        testToString(20, true, "G--2 S");
        testToString(33, false, "G#-3 _");
        testToString(10, true, "A--1 S");
        testToString(23, false, "A#-2 _");
        testToString(36, true, "B--3 S");
    }

    @Test
    void testToStringBlank() {
        event.clear();
        assertEquals("____ _", event.toString());
    }

    @Test
    void testToStringRest() {
        event.makeRest();
        assertEquals("STOP _", event.toString());
    }
}
