package kranji.svg;

import kranji.entry.ChineseCharacterEntry;
import kranji.stroke.*;
import kranji.stroke.glyph.GlyphDef;
import kranji.stroke.glyph.GlyphRegistry;

import java.util.List;

/**
 * Renders a {@link ChineseCharacterEntry} as an SVG with actual stroke paths.
 *
 * <p>Uses bottom-up sizing: leaf components have a fixed base size, parent nodes
 * compute their dimensions from children. Complex characters naturally produce
 * larger SVGs. Strokes are rendered as filled Bézier offset-curve outlines.</p>
 *
 * <p>Glyphs without stroke definitions fall back to centered text rendering.</p>
 */
public final class StrokeSvgRenderer {

    private static final String STROKE_FILL = "#1a1a1a";
    private static final String FALLBACK_FILL = "#555";
    private static final double PADDING = 20;
    private static final double HEADER_HEIGHT = 60;

    private StrokeSvgRenderer() {}

    /**
     * Render a character entry as a complete SVG document string.
     */
    public static String render(ChineseCharacterEntry entry) {
        // Pass 1: measure
        LayoutBox box = LayoutEngine.measure(entry.structure());

        // Pass 2: place
        List<LayoutEngine.PlacedGlyph> placed = LayoutEngine.place(
                entry.structure(), PADDING, HEADER_HEIGHT + PADDING, entry.character());

        double svgWidth = box.width() + 2 * PADDING;
        double svgHeight = box.height() + HEADER_HEIGHT + 2 * PADDING;

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%.0f\" height=\"%.0f\" viewBox=\"0 0 %.0f %.0f\">\n",
                svgWidth, svgHeight, svgWidth, svgHeight));
        sb.append("<style>\n");
        sb.append("  text { font-family: 'Microsoft YaHei', 'PingFang SC', 'Noto Sans CJK SC', sans-serif; }\n");
        sb.append("</style>\n");

        // Header: character + metadata
        appendHeader(sb, entry, svgWidth);

        // Render each placed glyph
        GlyphRegistry registry = GlyphRegistry.instance();
        for (LayoutEngine.PlacedGlyph pg : placed) {
            if (pg.glyph() == null) continue;

            var glyphDef = registry.lookup(pg.glyph());
            if (glyphDef.isPresent()) {
                renderStrokes(sb, glyphDef.get(), pg.transform());
            } else {
                renderFallbackText(sb, pg.glyph(), pg.transform());
            }
        }

        sb.append("</svg>\n");
        return sb.toString();
    }

    private static void appendHeader(StringBuilder sb, ChineseCharacterEntry entry, double svgWidth) {
        double cx = svgWidth / 2;
        // Character glyph
        sb.append(String.format(
                "<text x=\"%.1f\" y=\"22\" font-size=\"28\" text-anchor=\"middle\" dominant-baseline=\"central\" fill=\"#1a1a1a\">%s</text>\n",
                cx, entry.character()));
        // Metadata line
        String pinyinStr = entry.pinyin().stream()
                .map(p -> p.numberedTone())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        sb.append(String.format(
                "<text x=\"%.1f\" y=\"47\" font-size=\"12\" text-anchor=\"middle\" dominant-baseline=\"central\" fill=\"#666\">%s  ·  %s  ·  %d strokes</text>\n",
                cx, pinyinStr, entry.codepoint(), entry.strokes()));
    }

    private static void renderStrokes(StringBuilder sb, GlyphDef def, AffineTransform2D transform) {
        for (StrokeInstance stroke : def.strokes()) {
            StrokeInstance transformed = stroke.transform(transform);
            sb.append(StrokeOutline.toSvgPath(transformed, STROKE_FILL)).append('\n');
        }
    }

    private static void renderFallbackText(StringBuilder sb, String glyph, AffineTransform2D transform) {
        // Place text at the center of the transformed [0,1]² box
        Vec2 center = transform.apply(new Vec2(0.5, 0.5));
        double fontSize = transform.widthScale() * 0.7;
        sb.append(String.format(
                "<text x=\"%.1f\" y=\"%.1f\" font-size=\"%.1f\" text-anchor=\"middle\" dominant-baseline=\"central\" fill=\"%s\">%s</text>\n",
                center.x(), center.y(), fontSize, FALLBACK_FILL, glyph));
    }
}
