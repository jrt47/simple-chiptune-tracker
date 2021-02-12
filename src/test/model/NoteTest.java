package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {
    Note note;

    @Test
    void testNote() {
        note = new Note(20);
        assertEquals(20, note.getPitch());

        note = new Note(6);
        assertEquals(6, note.getPitch());
    }

    @Test
    void testTransposeUpWithinRange() {
        note = new Note(7);
        note.transpose(4);
        assertEquals(11, note.getPitch());
    }

    @Test
    void testTransposeDownWithinRange() {
        note = new Note(12);
        note.transpose(-3);
        assertEquals(9, note.getPitch());
    }

    @Test
    void testTransposeUpJustWithinRange() {
        note = new Note(Note.MAX_PITCH - 5);
        note.transpose(5);
        assertEquals(Note.MAX_PITCH, note.getPitch());
    }

    @Test
    void testTransposeDownJustWithinRange() {
        note = new Note(7);
        note.transpose(-6);
        assertEquals(1, note.getPitch());
    }

    @Test
    void testTransposeUpJustOutOfRange() {
        note = new Note(Note.MAX_PITCH - 3);
        note.transpose(4);
        assertEquals(Note.MAX_PITCH - 11, note.getPitch());
    }

    @Test
    void testTransposeDownJustOutOfRange() {
        note = new Note(4);
        note.transpose(-5);
        assertEquals(11, note.getPitch());
    }

    @Test
    void testTransposeUpOutOfRange() {
        note = new Note(Note.MAX_PITCH - 2);
        note.transpose(7);
        assertEquals(Note.MAX_PITCH - 7, note.getPitch());
    }

    @Test
    void testTransposeDownOutOfRange() {
        note = new Note(2);
        note.transpose(-5);
        assertEquals(9, note.getPitch());
    }

    @Test
    void testTransposeUpByOctaveWithinRange() {
        note = new Note(4);
        note.transposeUpByOctave();
        assertEquals(16, note.getPitch());
    }

    @Test
    void testTransposeUpByOctaveJustWithinRange() {
        note = new Note(Note.MAX_PITCH - 12);
        note.transposeUpByOctave();
        assertEquals(Note.MAX_PITCH, note.getPitch());
    }

    @Test
    void testTransposeUpByOctaveJustOutOfRange() {
        note = new Note(Note.MAX_PITCH - 11);
        note.transposeUpByOctave();
        assertEquals(Note.MAX_PITCH - 11, note.getPitch());
    }

    @Test
    void testTransposeUpByOctaveOutOfRange() {
        note = new Note(Note.MAX_PITCH - 7);
        note.transposeUpByOctave();
        assertEquals(Note.MAX_PITCH - 7, note.getPitch());
    }

    @Test
    void testTransposeDownByOctaveWithinRange() {
        note = new Note(15);
        note.transposeDownByOctave();
        assertEquals(3, note.getPitch());
    }

    @Test
    void testTransposeDownByOctaveJustWithinRange() {
        note = new Note(13);
        note.transposeDownByOctave();
        assertEquals(1, note.getPitch());
    }

    @Test
    void testTransposeDownByOctaveJustOutOfRange() {
        note = new Note(12);
        note.transposeDownByOctave();
        assertEquals(12, note.getPitch());
    }

    @Test
    void testTransposeDownByOctaveOutOfRange() {
        note = new Note(5);
        note.transposeDownByOctave();
        assertEquals(5, note.getPitch());
    }

    @Test
    void testToStringC1() {
        note = new Note(1);
        assertEquals("C--1 _", note.toString());
    }

    @Test
    void testToStringF1() {
        note = new Note(6);
        assertEquals("F--1 _", note.toString());
    }

    @Test
    void testToStringB1() {
        note = new Note(12);
        assertEquals("B--1 _", note.toString());
    }

    @Test
    void testToStringE2() {
        note = new Note(17);
        assertEquals("E--2 _", note.toString());
    }

    @Test
    void testToStringGSharp2() {
        note = new Note(21);
        assertEquals("G#-2 _", note.toString());
    }

    @Test
    void testToStringCSharp3() {
        note = new Note(26);
        assertEquals("C#-3 _", note.toString());
    }
}
