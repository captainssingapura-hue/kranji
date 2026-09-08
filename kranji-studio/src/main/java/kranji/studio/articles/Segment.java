package kranji.studio.articles;

import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.List;

/**
 * One part of a document, the size of a thing somebody sits down to read.
 *
 * <h2>Why a document is a tree</h2>
 *
 * <p>The grid builds roughly one cell per character. The sample document is
 * 3,232 characters and about 2,300 cells; the longest article the library
 * actually serves is 489. A long document is not a big article — it is a
 * <i>collection</i> of articles that happens to live in one file, and the tree
 * is what says so.</p>
 *
 * <p>See <i>Markdown Articles</i> for the measurements. The two that decide the
 * shape: splitting at headings alone brings the largest part to 461, already
 * under what renders today; and the published library's median article is 51
 * characters, which is what a reading session actually looks like.</p>
 *
 * <h2>A path is where it is; an id is what it is called</h2>
 *
 * <p>{@link #path} is a position — {@code 2.1} is the first subsection of the
 * second section — and it changes the moment anybody reorders anything. It is
 * for pointing at a node in a workbench, and for nothing that outlives the
 * session.</p>
 *
 * <p>{@link #id} is the author's pinned {@code {#slug}}, and empty when there
 * is none. It is an <b>address</b>: immutable once published, because a
 * bookmark that breaks when somebody improves a heading is worse than a heading
 * nobody improves. Nothing here invents one — a segment with no pinned id has
 * no address yet, and says so, which is what the write-back step will fix.</p>
 *
 * @param path     position in the tree, dotted. Empty on the document itself
 * @param id       the pinned {@code {#slug}}, or empty
 * @param level    1 for the document, 2 for {@code ##}, 3 for {@code ###}, and
 *                 0 for a part that a size budget cut out of a longer section
 * @param heading  the heading's own spans. Empty on a part, which has no
 *                 heading of its own and is named by the section it came from
 * @param blocks   the prose belonging to this segment directly
 * @param children subsections, and the parts of an over-long one, in reading
 *                 order
 */
public record Segment(String path, String id, int level,
                      List<Span> heading, List<Block> blocks, List<Segment> children) {

    public Segment {
        heading = List.copyOf(heading);
        blocks = List.copyOf(blocks);
        children = List.copyOf(children);
    }

    /** What the heading says, ignoring how it is divided into spans. */
    public String title() {
        return heading.stream().map(Span::text).reduce("", String::concat);
    }

    /** Whether the author gave this segment an address. */
    public boolean pinned() { return !id.isEmpty(); }

    /** A part cut out of a longer section by the size budget. */
    public boolean part() { return level == 0; }

    /** Characters in this segment's own prose — what the budget is spent on. */
    public int chars() {
        return blocks.stream()
                .flatMap(b -> b.lines().stream())
                .flatMap(List::stream)
                .mapToInt(s -> s.text().codePointCount(0, s.text().length()))
                .sum();
    }

    /** Characters in this segment and everything under it. */
    public int total() {
        return chars() + children.stream().mapToInt(Segment::total).sum();
    }

    /** Segments with prose of their own, in reading order — depth first. */
    public List<Segment> leaves() {
        if (children.isEmpty()) return List.of(this);
        var out = new java.util.ArrayList<Segment>();
        if (!blocks.isEmpty()) out.add(this);
        for (Segment child : children) out.addAll(child.leaves());
        return List.copyOf(out);
    }

    /** This segment and everything under it, in reading order. */
    public List<Segment> walk() {
        var out = new java.util.ArrayList<Segment>();
        out.add(this);
        for (Segment child : children) out.addAll(child.walk());
        return List.copyOf(out);
    }
}
