package kranji.layout;

import kranji.entry.ChineseCharacterEntry;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Renders a character's block layout as an SVG diagram.
 *
 * <p>Each {@link Block} is drawn as a colored rectangle with its glyph
 * label centered inside. Colors are assigned by depth level. Wrapper
 * blocks (enclosures) are rendered with dashed borders to show they
 * overlap with content blocks.</p>
 *
 * <p>The SVG is self-contained and uses a fixed canvas size with the
 * character's header (glyph + pinyin) displayed above the layout.</p>
 */
public final class BlockSvgRenderer {

    private static final int CANVAS = 400;     // px — the layout square
    private static final int PADDING = 20;     // px — around the canvas
    private static final int HEADER_H = 60;    // px — header area
    private static final double GAP = 1.5;     // px — visual gap between blocks

    /** Depth-indexed fill colors (semi-transparent). */
    private static final String[] FILLS = {
            "rgba(66,133,244,0.18)",   // depth 0 — blue
            "rgba(52,168,83,0.22)",    // depth 1 — green
            "rgba(251,188,4,0.25)",    // depth 2 — amber
            "rgba(234,67,53,0.22)",    // depth 3 — red
            "rgba(142,68,173,0.22)",   // depth 4 — purple
            "rgba(0,172,193,0.22)",    // depth 5 — teal
    };

    /** Depth-indexed border colors. */
    private static final String[] STROKES = {
            "#4285F4",
            "#34A853",
            "#FBC02D",
            "#EA4335",
            "#8E44AD",
            "#00ACC1",
    };

    /**
     * Reference font size for glyph rendering. The glyph is rendered at
     * this size then scaled via transform to fill the block.
     */
    private static final double BASE_FONT = 100;

    /** Fill factor — glyph fills 88% of each block dimension. */
    private static final double FILL_FACTOR = 0.88;

    /**
     * Stretch resistance — blends independent x/y scaling towards uniform scaling.
     * 0.0 = fully independent (current), 1.0 = perfectly square (uniform).
     * Geometric mean preserves visual area while softening extreme aspect ratios.
     */
    private static final double STRETCH_RESISTANCE = 0.10;

    private BlockSvgRenderer() {}

    /**
     * Render a character's block layout as a complete SVG document.
     */
    public static String render(ChineseCharacterEntry entry) {
        List<Block> blocks = BlockLayoutEngine.layout(entry);

        double svgW = CANVAS + 2 * PADDING;
        double svgH = CANVAS + HEADER_H + 2 * PADDING;

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%.0f\" height=\"%.0f\" viewBox=\"0 0 %.0f %.0f\">\n",
                svgW, svgH, svgW, svgH));
        sb.append("<style>\n");
        sb.append("  :root { --glyph-color: #000; --glyph-wrapper-opacity: 0.25; --block-display: inline; }\n");
        sb.append("  text { font-family: 'Microsoft YaHei', 'PingFang SC', 'Noto Sans CJK SC', sans-serif; }\n");
        sb.append("  .glyph { fill: var(--glyph-color); }\n");
        sb.append("  .glyph-wrapper { fill: var(--glyph-color); opacity: var(--glyph-wrapper-opacity); }\n");
        sb.append("  .block, .block-wrapper { display: var(--block-display); }\n");
        sb.append("</style>\n");

        // Background
        sb.append(String.format(
                "<rect width=\"%.0f\" height=\"%.0f\" fill=\"#fafafa\" rx=\"4\"/>\n", svgW, svgH));

        // Header
        appendHeader(sb, entry, svgW);

        // Layout canvas origin
        double ox = PADDING;
        double oy = HEADER_H + PADDING;

        // Render blocks — wrappers first (they go underneath)
        for (Block b : blocks) {
            if (b.role() == BlockRole.WRAPPER || b.role() == BlockRole.OUTER_FRAME) {
                renderBlock(sb, b, ox, oy, true);
            }
        }
        // Then regular blocks on top
        for (Block b : blocks) {
            if (b.role() != BlockRole.WRAPPER && b.role() != BlockRole.OUTER_FRAME) {
                renderBlock(sb, b, ox, oy, false);
            }
        }

        sb.append("</svg>\n");
        return sb.toString();
    }

    private static void appendHeader(StringBuilder sb, ChineseCharacterEntry entry, double svgW) {
        double cx = svgW / 2;
        sb.append(String.format(
                "<text x=\"%.1f\" y=\"24\" font-size=\"28\" font-weight=\"bold\" text-anchor=\"middle\" fill=\"#1a1a1a\">%s</text>\n",
                cx, entry.character()));
        String pinyin = entry.pinyin().stream()
                .map(p -> p.numberedTone())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        sb.append(String.format(
                "<text x=\"%.1f\" y=\"48\" font-size=\"13\" text-anchor=\"middle\" fill=\"#888\">%s  ·  %s  ·  %d strokes</text>\n",
                cx, pinyin, entry.codepoint(), entry.strokes()));
    }

    private static void renderBlock(StringBuilder sb, Block b, double ox, double oy,
                                     boolean isWrapper) {
        // Map normalized [0,1]² to canvas pixels
        double px = ox + b.x() * CANVAS + GAP;
        double py = oy + b.y() * CANVAS + GAP;
        double pw = b.w() * CANVAS - 2 * GAP;
        double ph = b.h() * CANVAS - 2 * GAP;

        if (pw <= 0 || ph <= 0) return;

        int colorIdx = b.depth() % FILLS.length;
        String fill = FILLS[colorIdx];
        String stroke = STROKES[colorIdx];

        if (isWrapper) {
            // Wrapper/frame: dashed border, very light fill
            sb.append(String.format(
                    "<rect class=\"block-wrapper\" x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" " +
                            "fill=\"rgba(200,200,200,0.08)\" stroke=\"%s\" stroke-width=\"1.5\" " +
                            "stroke-dasharray=\"6,3\" rx=\"3\"/>\n",
                    px, py, pw, ph, stroke));
        } else {
            // Regular block: solid fill + border
            sb.append(String.format(
                    "<rect class=\"block\" x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\" " +
                            "fill=\"%s\" stroke=\"%s\" stroke-width=\"1\" rx=\"3\"/>\n",
                    px, py, pw, ph, fill, stroke));
        }

        // Glyph rendering — scaled to fit visual bounds within block
        if (b.glyph() != null) {
            Rectangle2D vb = GlyphBounds.visualBounds(b.glyph());
            double vw = vb.getWidth();
            double vh = vb.getHeight();

            if (vw < 1 || vh < 1) return; // degenerate glyph

            // Scale so visual bounds fill the block (with fill factor)
            double rawSx = (FILL_FACTOR * pw) / vw;
            double rawSy = (FILL_FACTOR * ph) / vh;
            double sUniform = Math.sqrt(rawSx * rawSy);
            double sx = rawSx * (1 - STRETCH_RESISTANCE) + sUniform * STRETCH_RESISTANCE;
            double sy = rawSy * (1 - STRETCH_RESISTANCE) + sUniform * STRETCH_RESISTANCE;
            // Cap so visual bounds never exceed block
            sx = Math.min(sx, pw / vw);
            sy = Math.min(sy, ph / vh);

            if (sx < 0.04 || sy < 0.04) return; // too small

            // Position: visual center of scaled glyph at block center
            // In glyph space, visual center is at (vb.x + vw/2, vb.y + vh/2).
            // After transform translate(cx,cy) scale(sx,sy), that point maps to:
            //   (cx + (vb.x + vw/2)*sx,  cy + (vb.y + vh/2)*sy)
            // We want that to equal the block center (px+pw/2, py+ph/2).
            double blockCx = px + pw / 2.0;
            double blockCy = py + ph / 2.0;
            double cx = blockCx - (vb.getX() + vw / 2.0) * sx;
            double cy = blockCy - (vb.getY() + vh / 2.0) * sy;

            sb.append(String.format(
                    "<g transform=\"translate(%.2f,%.2f) scale(%.4f,%.4f)\" class=\"glyph\">" +
                            "<text x=\"0\" y=\"0\" font-size=\"%.0f\">%s</text></g>\n",
                    cx, cy, sx, sy, BASE_FONT, b.glyph()));
        }
    }

    private static String roleName(BlockRole role) {
        return switch (role) {
            case SINGULAR -> "singular";
            case LEFT -> "left";
            case RIGHT -> "right";
            case TOP -> "top";
            case BOTTOM -> "bottom";
            case LEFT_OF_THREE -> "left";
            case MIDDLE_H -> "mid";
            case RIGHT_OF_THREE -> "right";
            case TOP_OF_THREE -> "top";
            case MIDDLE_V -> "mid";
            case BOTTOM_OF_THREE -> "bottom";
            case OUTER_FRAME -> "frame";
            case INNER -> "inner";
            case WRAPPER -> "wrapper";
            case CONTENT -> "content";
            case MOSAIC_TOP -> "top";
            case MOSAIC_BOTTOM_LEFT -> "bot-L";
            case MOSAIC_BOTTOM_RIGHT -> "bot-R";
        };
    }
}
