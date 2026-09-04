package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * Measures how much of its own box a glyph actually fills.
 *
 * <p>Han characters all advance one em, but their ink does not: 光 reaches its
 * box edge while 月 stops well short. Anything placed <em>beside</em> a
 * character — punctuation, a marker — has to know which, or it will crowd one
 * and float away from the other.</p>
 *
 * <p>{@code canvas.measureText} reports the ink extent, so this is measurable
 * rather than guessable. Results are cached per (font, character): the same
 * character recurs constantly in a text and the measurement is not free.</p>
 */
public record GlyphMetricsModule() implements DomModule<GlyphMetricsModule> {

    /** Builds a measurer bound to one branch. */
    public record createGlyphMetrics() implements Exportable._Constant<GlyphMetricsModule> {}

    public static final GlyphMetricsModule INSTANCE = new GlyphMetricsModule();

    @Override
    public ImportsFor<GlyphMetricsModule> imports() {
        return ImportsFor.<GlyphMetricsModule>builder().build();
    }

    @Override
    public ExportsOf<GlyphMetricsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createGlyphMetrics()));
    }
}
