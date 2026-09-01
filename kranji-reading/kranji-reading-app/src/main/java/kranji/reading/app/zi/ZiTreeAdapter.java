package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.tree.DimensionKey;
import hue.captains.singapura.js.homing.tree.DimensionValue;
import hue.captains.singapura.js.homing.tree.NodeName;
import hue.captains.singapura.js.homing.tree.NormalizedNode;
import hue.captains.singapura.js.homing.tree.RowDisplay;
import hue.captains.singapura.js.homing.tree.TreeLevel;
import kranji.zi.tree.ZiBranch;
import kranji.zi.tree.ZiProjectionTree;
import kranji.zi.tree.ZiTerminal;
import kranji.zi.tree.ZiTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Turns a domain projection into the framework's normalized tree.
 *
 * <p>The whole framework-facing half of the Zi catalogue, and deliberately the
 * only place that knows both vocabularies. The domain side —
 * {@link ZiProjectionTree} and its nodes — names no framework type, so the
 * shape stays testable without a browser; this class does the translating.</p>
 *
 * <p>It is also the piece that survives. When the registry gains an ingest for
 * dynamic trees, a {@code NormalizedNode} is what it will accept, so this
 * output becomes the handover rather than being thrown away.</p>
 */
public final class ZiTreeAdapter {

    private ZiTreeAdapter() {}

    public static NormalizedNode toNormalized(ZiProjectionTree projection) {
        return node(projection.root(), ZiNodeIdentity.root(projection.projection()), 0);
    }

    private static NormalizedNode node(ZiTreeNode source, ZiNodeIdentity identity, int depth) {
        // Dimensions stay empty: display comes from RowDisplaySource, which is
        // how the framework own crate tree does it. DimensionKey is sealed to
        // framework types, so a downstream tree cannot add its own.
        Map<DimensionKey, DimensionValue> dims = Map.of();

        NodeName segment = new NodeName(source.segment());
        TreeLevel level = TreeLevel.atDepth(depth);

        if (source instanceof ZiTerminal) {
            return NormalizedNode.leaf(level, segment, identity, dims);
        }
        var children = new ArrayList<NormalizedNode>();
        for (ZiTreeNode child : source.children()) {
            children.add(node(child, identity.child(child.segment()), depth + 1));
        }
        return new NormalizedNode(level, segment, identity, dims, List.copyOf(children));
    }

    /**
     * Row display for the generic renderer.
     *
     * <p>A terminal shows its characters, which is the point of terminating at
     * a syllable: the payload is small enough to read in the tree itself.</p>
     */
    /**
     * How many glyphs a terminal shows before it stops listing and counts.
     *
     * <p>A row is one line. At seed scale every syllable fitted, but over the
     * full standard set the heaviest holds 74 characters and 57 syllables hold
     * more than twenty — so the list has to stop somewhere. Twelve is about
     * what reads as a preview rather than a truncation, and the characters
     * widget carries the full set anyway.</p>
     */
    private static final int GLYPH_PREVIEW = 12;

    public static RowDisplay rowFor(ZiTreeNode source) {
        if (source instanceof ZiTerminal t) {
            return new RowDisplay(source.label(), String.valueOf(t.characterCount()),
                    detailOf(source), "syllable");
        }
        return new RowDisplay(source.label(), String.valueOf(source.characterCount()),
                detailOf(source), source.segment().equals("phonic") ? "projection" : "group");
    }

    private static String detailOf(ZiTreeNode source) {
        if (source instanceof ZiTerminal t) {
            var glyphs = new StringBuilder();
            t.characters().stream().limit(GLYPH_PREVIEW).forEach(c -> glyphs.append(c.value()));
            int hidden = t.characterCount() - GLYPH_PREVIEW;
            // Say how many are not shown rather than trailing off - an ellipsis
            // alone hides whether two are missing or sixty.
            return hidden > 0 ? glyphs + " +" + hidden : glyphs.toString();
        }
        // NOTE: this counts appearances, not distinct characters - a polyphonic
        // character is filed under every reading it has. See KI-003.
        int n = source.characterCount();
        return n + (n == 1 ? " reading" : " readings");
    }

    /** Every node of the domain tree, flattened, for building a lookup. */
    public static List<ZiTreeNode> flatten(ZiBranch root) {
        var out = new ArrayList<ZiTreeNode>();
        walk(root, out);
        return List.copyOf(out);
    }

    private static void walk(ZiTreeNode node, List<ZiTreeNode> out) {
        out.add(node);
        node.children().forEach(c -> walk(c, out));
    }
}
