package ui.sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

// Represents an oscillator that can play notes to the user at a given pitch
public abstract class Oscillator {
    private static final double[] FREQUENCIES = generateFrequencies();
    private static final double A0_FREQUENCY = 55;
    protected static final int SAMPLE_RATE = 48000;
    protected static final int NUM_WAVELENGTHS = 12;

    protected Clip clip;

    // MODIFIES: this
    // EFFECTS: plays a note using this at the given pitch
    public void play(int pitch) {
        try {
            stop();
            clip = AudioSystem.getClip();
            loadWaveform(pitch);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the oscillator is playing a note, stops playing the note
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the oscillator's waveform for the given pitch
    protected void loadWaveform(int pitch) throws LineUnavailableException {
        double frequency = getFrequency(pitch);
        double period = 1 / frequency;
        int bufferSize = (int) Math.round(period * NUM_WAVELENGTHS * SAMPLE_RATE);
        byte[] buffer = new byte[bufferSize];

        for (int i = 0; i < bufferSize; i++) {
            buffer[i] = getSample(i, period * SAMPLE_RATE);
        }

        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(buffer), audioFormat, bufferSize);

        try {
            clip.open(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: returns the sample at the given position along the waveform for the given wavelength depending on the
    //          oscillator type
    protected byte getSample(int pos, double wavelength) {
        return 0;
    }

    // EFFECTS: returns the frequency in Hz corresponding to the given pitch
    protected static double getFrequency(int pitch) {
        int relPitch = (pitch - 1) % 12;
        int octave = (pitch - 1) / 12;
        return FREQUENCIES[relPitch] * Math.pow(2, octave);
    }

    // EFFECTS: generates an array of the frequencies of the notes from C1-B1
    private static double[] generateFrequencies() {
        double[] frequencies = new double[12];
        for (int i = 0; i <= 11; i++) {
            double n = i + 3;
            frequencies[i] = (A0_FREQUENCY * Math.pow(2, n / 12));
        }
        return frequencies;
    }
}
