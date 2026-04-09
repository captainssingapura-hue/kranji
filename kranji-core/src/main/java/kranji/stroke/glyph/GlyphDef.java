package kranji.stroke.glyph;

import kranji.stroke.StrokeInstance;

import java.util.List;

/**
 * Definition of a single glyph (leaf component) as an ordered list of strokes.
 *
 * <p>All stroke coordinates are in normalized [0,1]² space where (0,0) is
 * top-left and (1,1) is bottom-right.</p>
 *
 * @param glyph   the Unicode character string (e.g. "口", "日")
 * @param strokes ordered list of strokes composing this glyph
 */
public record GlyphDef(String glyph, List<StrokeInstance> strokes) {

    public GlyphDef {
        strokes = List.copyOf(strokes);
    }
}
