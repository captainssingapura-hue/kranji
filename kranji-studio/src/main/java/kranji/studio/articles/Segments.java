package kranji.studio.articles;

import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.ArrayList;
import java.util.List;

/**
 * A document, cut into segments.
 *
 * <h2>Two cuts, and they answer different questions</h2>
 *
 * <p><b>Headings</b> give the structure, and for <i>display</i> they are
 * enough. The sample document is 3,232 characters — about 2,300 cell widgets,
 * against the 489-character article that is the longest the library actually
 * serves. Split at {@code ##} and {@code ###} the largest section is 461, which
 * already renders.</p>
 *
 * <p><b>The budget</b> answers the other question, which display had hidden. A
 * median section of 185 characters is 3.6× the library's median article of 51.
 * That is not a broken page; it is a different kind of sitting. So a section
 * over budget is subdivided at paragraph boundaries — <b>never inside one</b>,
 * because a paragraph is the unit the reader already wraps.</p>
 *
 * <p>Headings first, budget second, and they stay separate: turning the budget
 * off leaves a tree that still renders, which is what makes the budget a policy
 * rather than a load-bearing part of the format.</p>
 */
public final class Segments {

    private Segments() {}

    /**
     * How long a segment should be, in characters.
     *
     * <p>The published library measures 51 at the median and 71 at the 90th
     * percentile over 608 articles. That is not a limit anybody imposed — it is
     * the length a person who writes these actually writes — so it is the right
     * thing to aim a budget at. At this figure the sample document yields
     * roughly forty leaves, each the size of a real article.</p>
     *
     * <p>It is a <b>policy</b>, not a rule of the format. {@link #NO_BUDGET}
     * turns it off and the tree is still a tree.</p>
     */
    public static final int BUDGET = 70;

    /** Headings only: the structural cut, with no size policy on top. */
    public static final int NO_BUDGET = Integer.MAX_VALUE;

    /** The document as a tree, at the default budget. */
    public static Segment of(List<Block> blocks) {
        return of(blocks, BUDGET);
    }

    public static Segment of(List<Block> blocks, int budget) {
        return split(headings(blocks), Math.max(1, budget));
    }

    // ── The structural cut ─────────────────────────────────────────────

    /** One node being built. Mutable while its blocks are still arriving. */
    private static final class Node {
        // The document's own heading and address arrive as a block, after the
        // node they belong to has had to exist. Everything else is named when
        // it is made.
        String id;
        final int level;
        final List<Span> heading = new ArrayList<>();
        final List<Block> blocks = new ArrayList<>();
        final List<Node> children = new ArrayList<>();

        Node(String id, int level, List<Span> heading) {
            this.id = id;
            this.level = level;
            this.heading.addAll(heading);
        }
    }

    /**
     * The tree the headings describe.
     *
     * <p>A {@code ##} closes any open {@code ###} and starts a section; a
     * {@code ###} starts a subsection under it. Anything else is prose and
     * belongs to whichever node is innermost — including prose before the first
     * heading, which belongs to the document itself.</p>
     */
    private static Node headings(List<Block> blocks) {
        Node document = new Node("", 1, List.of());
        Node section = null;
        Node subsection = null;

        for (Block block : blocks) {
            if (block.kind().equals("title")) {
                // The document's own heading and address, and the reason the
                // root is a segment rather than a bare list of sections. A
                // whole document is a thing somebody links to.
                document.heading.addAll(block.lines().isEmpty()
                        ? List.of() : block.lines().get(0));
                document.id = block.id();
                continue;
            }
            if (block.kind().equals("heading")) {
                List<Span> text = block.lines().isEmpty() ? List.of() : block.lines().get(0);
                if (block.level() <= 2) {
                    section = new Node(block.id(), 2, text);
                    document.children.add(section);
                    subsection = null;
                } else {
                    subsection = new Node(block.id(), 3, text);
                    // A ### before any ## hangs off the document rather than
                    // being dropped. The subset allows it and an author who
                    // writes it meant something by it.
                    (section == null ? document : section).children.add(subsection);
                }
                continue;
            }
            Node into = subsection != null ? subsection
                      : section != null ? section
                      : document;
            into.blocks.add(block);
        }
        return document;
    }

    // ── The size budget ────────────────────────────────────────────────

    /** Freezes the tree, subdividing anything over budget as it goes. */
    private static Segment split(Node node, int budget) {
        var children = new ArrayList<Segment>();

        // Parts come before subsections, because the prose they were cut from
        // came before them. Reading order is the only order a tree of an
        // article can be in.
        List<List<Block>> parts = parts(node.blocks, budget);
        List<Block> own = parts.size() > 1 ? List.of() : node.blocks;
        if (parts.size() > 1) {
            for (List<Block> part : parts) {
                children.add(new Segment("", "", 0, List.of(), part, List.of()));
            }
        }
        for (Node child : node.children) children.add(split(child, budget));

        return placed(new Segment("", node.id, node.level, node.heading, own, children), "");
    }

    /**
     * Numbers the tree.
     *
     * <p>Done in one pass at the end rather than while building, so a path
     * always describes the finished tree — a part inserted by the budget shifts
     * the subsections after it, and a number handed out early would have been
     * wrong.</p>
     */
    private static Segment placed(Segment segment, String path) {
        var children = new ArrayList<Segment>();
        for (int i = 0; i < segment.children().size(); i++) {
            String childPath = path.isEmpty() ? String.valueOf(i + 1) : path + "." + (i + 1);
            children.add(placed(segment.children().get(i), childPath));
        }
        return new Segment(path, segment.id(), segment.level(),
                           segment.heading(), segment.blocks(), children);
    }

    /**
     * One node's prose, cut into pieces that fit the budget.
     *
     * <p>Whole blocks only. A paragraph longer than the budget on its own stays
     * whole and goes over — cutting inside one would break the thing the reader
     * wraps, and a paragraph is short enough that going over is the smaller
     * harm.</p>
     *
     * <p>Returns one piece when nothing needed cutting, which is how the caller
     * tells that no parts were made.</p>
     */
    private static List<List<Block>> parts(List<Block> blocks, int budget) {
        if (blocks.isEmpty() || size(blocks) <= budget) return List.of(blocks);

        var parts = new ArrayList<List<Block>>();
        var current = new ArrayList<Block>();
        int used = 0;

        for (Block block : blocks) {
            int cost = size(List.of(block));
            if (!current.isEmpty() && used + cost > budget) {
                parts.add(List.copyOf(current));
                current.clear();
                used = 0;
            }
            current.add(block);
            used += cost;
        }
        if (!current.isEmpty()) parts.add(List.copyOf(current));
        return List.copyOf(parts);
    }

    /** Characters, which is what the library's own distribution is measured in. */
    private static int size(List<Block> blocks) {
        return blocks.stream()
                .flatMap(b -> b.lines().stream())
                .flatMap(List::stream)
                .mapToInt(s -> s.text().codePointCount(0, s.text().length()))
                .sum();
    }
}
