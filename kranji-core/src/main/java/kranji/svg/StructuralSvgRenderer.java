package kranji.svg;

import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

/**
 * Renders a {@link Zi} as an SVG diagram showing recursive structural
 * decomposition.
 *
 * <p>Each composition variant maps to a spatial subdivision of the bounding
 * rectangle. Leaf {@link SingularBlock} nodes render their glyph as text.
 * Nested {@link ComposedBlock} nodes recurse, with font size and
 * stroke width scaling down by {@value #SCALE_FACTOR} per level.</p>
 */
public final class StructuralSvgRenderer {

    private StructuralSvgRenderer() {}

    // ── Constants ──────────────────────────────────────────────────────

    private static final double DEFAULT_SIZE = 400.0;
    private static final double SCALE_FACTOR = 0.75;
    private static final double BASE_FONT_SIZE = 64.0;
    private static final double HEADER_HEIGHT = 60.0;
    private static final String FONT_STACK =
            "'Microsoft YaHei', 'PingFang SC', 'Noto Sans CJK SC', 'Noto Sans CJK', sans-serif";

    private static final String[] LEVEL_COLORS = {
            "#2563EB", // blue
            "#DC2626", // red
            "#16A34A", // green
            "#CA8A04", // amber
            "#9333EA", // purple
    };

    private record Rect(double x, double y, double w, double h) {
        double cx() { return x + w / 2; }
        double cy() { return y + h / 2; }
    }

    // ── Public entry points ────────────────────────────────────────────

    public static String render(Zi entry) {
        return render(entry, DEFAULT_SIZE);
    }

    public static String render(Zi entry, double size) {
        var sb = new StringBuilder();
        double totalHeight = size + HEADER_HEIGHT;

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\"")
          .append(" width=\"").append(fmt(size))
          .append("\" height=\"").append(fmt(totalHeight))
          .append("\" viewBox=\"0 0 ").append(fmt(size)).append(' ').append(fmt(totalHeight))
          .append("\">\n");

        // style
        sb.append("<style>\n");
        sb.append("  text { font-family: ").append(FONT_STACK).append("; }\n");
        sb.append("  .node-rect { fill-opacity: 0.04; }\n");
        sb.append("  .wrapper-rect { fill-opacity: 0.06; stroke-dasharray: 6,3; }\n");
        sb.append("  .leaf-glyph { dominant-baseline: central; text-anchor: middle; }\n");
        sb.append("  .label { font-size: 10px; fill: #888; dominant-baseline: hanging; }\n");
        sb.append("</style>\n");

        // header
        emitHeader(sb, entry, size);

        // decomposition area
        sb.append("<g transform=\"translate(0,").append(fmt(HEADER_HEIGHT)).append(")\">\n");
        var bounds = new Rect(0, 0, size, size);
        renderNode(sb, entry.structure(), bounds, 0, entry.character());
        sb.append("</g>\n");

        sb.append("</svg>\n");
        return sb.toString();
    }

    // ── Core recursion ─────────────────────────────────────────────────

    private static void renderNode(StringBuilder sb, BlockStructure node,
                                   Rect bounds, int depth, String parentGlyph) {
        switch (node) {
            case ComposedBlock comp -> {
                emitRect(sb, bounds, depth, compositionLabel(comp), false);
                renderComposition(sb, comp, bounds, depth, parentGlyph);
            }
            default -> {
                String glyph = node.glyph();
                String label = glyph;
                if (node instanceof SingularBlock sing) {
                    String name = sing.name();
                    if (name != null && !name.equals(glyph)) label = glyph + " " + name;
                }
                renderLeaf(sb, glyph, label, bounds, depth);
            }
        }
    }

    private static void renderComposable(StringBuilder sb, Composable c,
                                         Rect bounds, int depth, String parentGlyph) {
        renderNode(sb, resolve(c), bounds, depth, parentGlyph);
    }

    private static BlockStructure resolve(Composable c) {
        return switch (c) {
            case Composable.OfZi(var zi) -> zi.structure();
            case Composable.OfBlock(var block) -> block;
        };
    }

    // ── Composition dispatch ───────────────────────────────────────────

    private static void renderComposition(StringBuilder sb,
                                          ComposedBlock comp,
                                          Rect bounds, int depth,
                                          String parentGlyph) {
        int next = depth + 1;
        switch (comp) {
            case LeftRight lr -> {
                double mid = bounds.w / 2;
                renderComposable(sb, lr.left(),
                        new Rect(bounds.x, bounds.y, mid, bounds.h), next, null);
                renderComposable(sb, lr.right(),
                        new Rect(bounds.x + mid, bounds.y, mid, bounds.h), next, null);
            }

            case TopBottom tb -> {
                double mid = bounds.h / 2;
                renderComposable(sb, tb.top(),
                        new Rect(bounds.x, bounds.y, bounds.w, mid), next, null);
                renderComposable(sb, tb.bottom(),
                        new Rect(bounds.x, bounds.y + mid, bounds.w, mid), next, null);
            }

            case LeftMiddleRight lmr -> {
                double third = bounds.w / 3;
                renderComposable(sb, lmr.left(),
                        new Rect(bounds.x, bounds.y, third, bounds.h), next, null);
                renderComposable(sb, lmr.middle(),
                        new Rect(bounds.x + third, bounds.y, third, bounds.h), next, null);
                renderComposable(sb, lmr.right(),
                        new Rect(bounds.x + 2 * third, bounds.y, third, bounds.h), next, null);
            }

            case TopMiddleBottom tmb -> {
                double third = bounds.h / 3;
                renderComposable(sb, tmb.top(),
                        new Rect(bounds.x, bounds.y, bounds.w, third), next, null);
                renderComposable(sb, tmb.middle(),
                        new Rect(bounds.x, bounds.y + third, bounds.w, third), next, null);
                renderComposable(sb, tmb.bottom(),
                        new Rect(bounds.x, bounds.y + 2 * third, bounds.w, third), next, null);
            }

            case FullEnclosure fe -> {
                renderEnclosure(sb, fe.outer(), fe.inner(), bounds, next,
                        0.20, 0.20, 0.60, 0.60);
            }

            case SemiEnclosureUpperLeft se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.25, 0.25, 0.75, 0.75);
            }

            case SemiEnclosureUpperRight se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.0, 0.25, 0.75, 0.75);
            }

            case SemiEnclosureBottomLeft se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.15, 0.0, 0.85, 0.75);
            }

            case SemiEnclosureTopThree se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.15, 0.15, 0.70, 0.70);
            }

            case SemiEnclosureBottomThree se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.15, 0.05, 0.70, 0.70);
            }

            case SemiEnclosureLeftThree se -> {
                renderEnclosure(sb, se.wrapper(), se.content(), bounds, next,
                        0.20, 0.15, 0.65, 0.70);
            }
        }
    }

    // ── Enclosure helper ───────────────────────────────────────────────

    private static void renderEnclosure(StringBuilder sb,
                                        Composable wrapper, Composable content,
                                        Rect bounds, int depth,
                                        double inX, double inY,
                                        double inW, double inH) {
        String wrapperGlyph = wrapper.glyph();
        emitRect(sb, bounds, depth, wrapperGlyph, true);
        emitText(sb, wrapperGlyph, bounds, depth, 0.15);

        var inner = new Rect(
                bounds.x + bounds.w * inX,
                bounds.y + bounds.h * inY,
                bounds.w * inW,
                bounds.h * inH
        );
        renderComposable(sb, content, inner, depth, null);
    }

    // ── Leaf rendering ─────────────────────────────────────────────────

    private static void renderLeaf(StringBuilder sb, String glyph,
                                   String label, Rect bounds, int depth) {
        emitRect(sb, bounds, depth, label != null ? label : glyph, false);
        emitText(sb, glyph, bounds, depth);
    }

    // ── SVG element emitters ───────────────────────────────────────────

    private static void emitRect(StringBuilder sb, Rect r, int depth,
                                 String tooltip, boolean dashed) {
        String color = colorForDepth(depth);
        double sw = Math.max(0.75, 2.0 * Math.pow(SCALE_FACTOR, depth));
        String cls = dashed ? "wrapper-rect" : "node-rect";

        sb.append("  <rect class=\"").append(cls).append('"')
          .append(" x=\"").append(fmt(r.x))
          .append("\" y=\"").append(fmt(r.y))
          .append("\" width=\"").append(fmt(r.w))
          .append("\" height=\"").append(fmt(r.h))
          .append("\" rx=\"3\" ry=\"3\"")
          .append(" stroke=\"").append(color)
          .append("\" stroke-width=\"").append(fmt(sw))
          .append("\" fill=\"").append(color).append('"');
        sb.append(">\n");
        if (tooltip != null) {
            sb.append("    <title>").append(escXml(tooltip)).append("</title>\n");
        }
        sb.append("  </rect>\n");
    }

    private static void emitText(StringBuilder sb, String glyph,
                                 Rect r, int depth) {
        emitText(sb, glyph, r, depth, 1.0);
    }

    private static void emitText(StringBuilder sb, String glyph,
                                 Rect r, int depth, double opacity) {
        double fs = fontSize(depth);
        fs = Math.min(fs, Math.min(r.w * 0.8, r.h * 0.8));
        String color = colorForDepth(depth);

        sb.append("  <text class=\"leaf-glyph\"")
          .append(" x=\"").append(fmt(r.cx()))
          .append("\" y=\"").append(fmt(r.cy()))
          .append("\" font-size=\"").append(fmt(fs))
          .append("\" fill=\"").append(color).append('"');
        if (opacity < 1.0) {
            sb.append(" opacity=\"").append(fmt(opacity)).append('"');
        }
        sb.append(">").append(escXml(glyph)).append("</text>\n");
    }

    private static void emitHeader(StringBuilder sb, Zi entry,
                                   double width) {
        sb.append("<text x=\"").append(fmt(width / 2))
          .append("\" y=\"22\" font-size=\"28\" text-anchor=\"middle\"")
          .append(" dominant-baseline=\"central\"")
          .append(" fill=\"#1a1a1a\"")
          .append(" font-family=\"").append(FONT_STACK).append("\">")
          .append(escXml(entry.character()))
          .append("</text>\n");

        String pinyin = entry.pinyin().stream()
                .map(p -> p.base() + p.tone().number())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        String meta = pinyin + "  ·  " + entry.codepoint()
                + "  ·  " + entry.strokes() + " strokes"
                + "  ·  radical " + entry.radicalNo();
        sb.append("<text x=\"").append(fmt(width / 2))
          .append("\" y=\"47\" font-size=\"12\" text-anchor=\"middle\"")
          .append(" dominant-baseline=\"central\"")
          .append(" fill=\"#666\">")
          .append(escXml(meta))
          .append("</text>\n");
    }

    // ── Helpers ─────────────────────────────────────────────────────────

    private static double fontSize(int depth) {
        return BASE_FONT_SIZE * Math.pow(SCALE_FACTOR, depth);
    }

    private static String colorForDepth(int depth) {
        return LEVEL_COLORS[depth % LEVEL_COLORS.length];
    }

    private static String compositionLabel(ComposedBlock comp) {
        return switch (comp) {
            case LeftRight lr -> "Left-Right";
            case TopBottom tb -> "Top-Bottom";
            case LeftMiddleRight lmr -> "Left-Middle-Right";
            case TopMiddleBottom tmb -> "Top-Middle-Bottom";
            case FullEnclosure fe -> "Full Enclosure";
            case SemiEnclosureUpperLeft se -> "Semi ↰ Upper-Left";
            case SemiEnclosureUpperRight se -> "Semi ↱ Upper-Right";
            case SemiEnclosureBottomLeft se -> "Semi ↲ Bottom-Left";
            case SemiEnclosureTopThree se -> "Semi ⊓ Top-Three";
            case SemiEnclosureBottomThree se -> "Semi ⊔ Bottom-Three";
            case SemiEnclosureLeftThree se -> "Semi ⊏ Left-Three";
        };
    }

    private static String fmt(double v) {
        if (v == (long) v) return Long.toString((long) v);
        return String.format("%.2f", v);
    }

    private static String escXml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
