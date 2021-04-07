package ui.sound;

// Represents an oscillator that produces a pulse wave
public class PulseOscillator extends Oscillator {
    private static final byte AMPLITUDE = 20;
    private final int pulseWidth;

    // EFFECTS: constructs the pulse oscillator with the given pulseWidth
    public PulseOscillator(int pulseWidth) {
        this.pulseWidth = pulseWidth;
    }

    // EFFECTS: see super
    @Override
    protected byte getSample(int pos, double wavelength) {
        if (pos % wavelength < wavelength * (pulseWidth / 100.0)) {
            return AMPLITUDE;
        } else {
            return -AMPLITUDE;
        }
    }
}
