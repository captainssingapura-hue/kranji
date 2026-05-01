package kranji.ui.threed.graph;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * Procedural paint factory for glyph strokes — used as the {@code fill}
 * on each extruded {@code Text} layer to give the otherwise flat
 * characters a bit of visual material.
 *
 * <p>All textures are gradients; no image assets needed.</p>
 */
public final class GlyphTextures {

    /** Stable identifiers — also the labels shown in pickers. */
    public static final String SOLID    = "Solid";
    public static final String INK_WASH = "Ink wash";
    public static final String CINNABAR = "Cinnabar";
    public static final String JADE     = "Jade";
    public static final String GOLD_LEAF = "Gold leaf";

    public static final String[] ALL = { SOLID, INK_WASH, CINNABAR, JADE, GOLD_LEAF };

    private GlyphTextures() {}

    /** Build the named paint, biased toward {@code base} where it makes sense. */
    public static Paint forName(String name, Color base) {
        if (name == null) return base;
        return switch (name) {
            case INK_WASH  -> inkWash(base);
            case CINNABAR  -> cinnabar();
            case JADE      -> jade();
            case GOLD_LEAF -> goldLeaf();
            default        -> base;
        };
    }

    /** Vertical gradient: top dark, bottom slightly lighter — calligraphy ink feel. */
    private static LinearGradient inkWash(Color base) {
        Color top    = base.deriveColor(0, 1, 0.55, 1);
        Color bottom = base.deriveColor(0, 1, 1.25, 1);
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, top), new Stop(1, bottom));
    }

    /** Vermillion → amber radial — Chinese seal / chop aesthetic. */
    private static RadialGradient cinnabar() {
        return new RadialGradient(0, 0, 0.35, 0.35, 0.7, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(255, 188, 88)),
                new Stop(0.6, Color.rgb(204,  50,  40)),
                new Stop(1.0, Color.rgb(120,  20,  20)));
    }

    /** Cool green sweep with a creamy highlight. */
    private static LinearGradient jade() {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(232, 246, 224)),
                new Stop(0.4, Color.rgb(120, 180, 130)),
                new Stop(1.0, Color.rgb( 30,  90,  60)));
    }

    /** Warm gold gradient — thin lacquer / leaf look. */
    private static LinearGradient goldLeaf() {
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(255, 232, 150)),
                new Stop(0.5, Color.rgb(212, 168,  60)),
                new Stop(1.0, Color.rgb(120,  84,  20)));
    }
}
