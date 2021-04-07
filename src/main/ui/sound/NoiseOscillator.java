package ui.sound;

import model.Event;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// Represents an oscillator that produces a noise sound
public class NoiseOscillator extends Oscillator {
    private static final String FILE_PATH = "./data/noise.wav";

    // MODIFIES: this
    // EFFECTS: see super
    @Override
    protected void loadWaveform(int pitch) throws LineUnavailableException {
        File audioFile = new File(FILE_PATH);
        AudioInputStream inStream;
        try {
            inStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return;
        }
        float sampleRate = getSampleRate(pitch);
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        AudioInputStream outStream = AudioSystem.getAudioInputStream(format, inStream);

        try {
            clip.open(outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: returns the sample rate for noise depending on the given pitch
    private static float getSampleRate(int pitch) {
        int basePitch = Event.MAX_PITCH - 13;
        double baseFrequency = getFrequency(basePitch);
        double frequency = getFrequency(pitch);
        double multiplier = Math.pow(frequency / baseFrequency, 1.8);
        return (float) (SAMPLE_RATE * multiplier);
    }
}
