package ui.sound;

// Represents an oscillator that produces a triangle wave
public class TriangleOscillator extends Oscillator {
    private static final byte AMPLITUDE = 55;

    // EFFECTS: see super
    @Override
    protected byte getSample(int pos, double wavelength) {
        double slope = 4 * AMPLITUDE / wavelength;
        int sample = (int) Math.round(slope * (pos % wavelength));
        if (sample > 3 * AMPLITUDE) {
            return (byte) (sample - 4 * AMPLITUDE);
        } else if (sample > AMPLITUDE) {
            return (byte) (2 * AMPLITUDE - sample);
        } else {
            return (byte) sample;
        }
    }
}
