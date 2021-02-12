package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaccatoNoteTest {
    StaccatoNote note;

    @Test
    void testNote() {
        note = new StaccatoNote(20);
        assertEquals(20, note.getPitch());

        note = new StaccatoNote(6);
        assertEquals(6, note.getPitch());
    }

    @Test
    void testToStringC1() {
        note = new StaccatoNote(1);
        assertEquals("C--1 S", note.toString());
    }

    @Test
    void testToStringFSharp1() {
        note = new StaccatoNote(7);
        assertEquals("F#-1 S", note.toString());
    }

    @Test
    void testToStringF2() {
        note = new StaccatoNote(18);
        assertEquals("F--2 S", note.toString());
    }

    @Test
    void testToStringB2() {
        note = new StaccatoNote(24);
        assertEquals("B--2 S", note.toString());
    }

    @Test
    void testToStringDSharp3() {
        note = new StaccatoNote(28);
        assertEquals("D#-3 S", note.toString());
    }

    @Test
    void testToStringA3() {
        note = new StaccatoNote(34);
        assertEquals("A--3 S", note.toString());
    }
}
