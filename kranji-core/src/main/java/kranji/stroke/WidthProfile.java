package kranji.stroke;

/**
 * Preset thickness profiles for calligraphic stroke rendering.
 *
 * <p>Each profile is a function [0,1] → [0,1] that modulates the stroke's
 * base width along the spine parameter t. The rendered half-width at
 * parameter t is: {@code baseWidth * profile(t) / 2}.</p>
 */
public enum WidthProfile {

    /** Constant width along the entire stroke. */
    UNIFORM {
        @Override public double at(double t) { return 1.0; }
    },

    /** Starts full, tapers to a point at t=1. Classic 撇 (piě) shape. */
    TAPER_END {
        @Override public double at(double t) { return 1.0 - t; }
    },

    /** Starts thin, swells to full width at t=1. Classic 捺 (nà) shape. */
    SWELL_END {
        @Override public double at(double t) { return t; }
    },

    /** Dot shape: swells in the middle, tapers at both ends. Classic 点 (diǎn). */
    DOT {
        @Override public double at(double t) { return 4 * t * (1 - t); }
    },

    /** Starts thin, rises to full width by mid-stroke, holds steady. Classic 提 (tí). */
    RISING {
        @Override public double at(double t) { return Math.min(2 * t, 1.0); }
    };

    /**
     * Evaluate the width multiplier at spine parameter t ∈ [0, 1].
     *
     * @return a value in [0, 1] that scales the stroke's base width
     */
    public abstract double at(double t);
}
