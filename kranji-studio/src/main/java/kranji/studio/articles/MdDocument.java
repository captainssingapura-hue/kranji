package kranji.studio.articles;

import java.util.List;

/**
 * A parsed draft, as structure rather than as markup.
 *
 * <h2>Why not HTML</h2>
 *
 * <p>An earlier version of this returned a string of HTML, and that one
 * decision reached all the way into the widget: a string of markup can only be
 * injected, so the pane called {@code innerHTML} and built the rest with
 * {@code document.createElement}. Both bypass the branch that owns a widget's
 * DOM — elements nothing created through {@code branch.createElement} are never
 * dissolved, and assigning {@code innerHTML} is the wholesale destruction the
 * framework's default rules exist to prevent.</p>
 *
 * <p>So the parser hands over what it found and the pane decides what to build
 * from it. That is also what stage two needs: a table and a table of contents
 * are two renderings of this same structure, and neither can be got out of a
 * string of {@code <p>} tags.</p>
 *
 * <p>It makes the parser easier to hold to account as well. A test can assert
 * that a line produced a ruby span with a particular reading, rather than
 * matching a substring of markup and hoping the markup does not change.</p>
 */
public final class MdDocument {

    private MdDocument() {}

    /**
     * One piece of a line.
     *
     * <h2>Emphasis is not a kind</h2>
     *
     * <p>It was, briefly, and that was wrong: it made emphasis and a pinned
     * reading alternatives, so a character could be one or the other. They are
     * two different questions about the same character — what it says, and how
     * heavily it is said — and an emphasised 字{zì} needs both answered.</p>
     *
     * @param kind     {@code text}, or {@code ruby} for a character carrying a
     *                 reading
     * @param text     what it says
     * @param reading  the reading, on a {@code ruby} span and empty otherwise
     * @param emphasis {@code strong}, {@code em}, or empty. How it is <i>set</i>
     *                 depends on whether it is inside a run, and that is the
     *                 renderer's business rather than this record's
     * @param inRun    inside {@code ‹…›}. Consecutive spans sharing this make one
     *                 run: flat here, grouped by whatever draws it, so that
     *                 nesting never has to travel over the wire
     */
    public record Span(String kind, String text, String reading,
                       String emphasis, boolean inRun) {}

    /**
     * One block.
     *
     * @param kind  {@code title}, {@code heading}, {@code p}, {@code li},
     *              {@code oli}, {@code quote} or {@code verse}
     * @param level 1..3 on a heading, the author's own number on an {@code oli},
     *              0 otherwise. A renderer that counted ordered items for itself
     *              would silently renumber a list that starts at 3
     * @param id    the pinned {@code {#slug}} on a heading, empty when it has
     *              none — which is worth showing, because a heading with no id
     *              has no address a reader can keep
     * @param lines one for every block but {@code verse}, which is the whole
     *              reason it exists: markdown joins the lines of a paragraph and
     *              a poem needs them kept
     */
    public record Block(String kind, int level, String id, List<List<Span>> lines) {

        public static Block of(String kind, List<Span> line) {
            return new Block(kind, 0, "", List.of(line));
        }

        public static Block heading(int level, String id, List<Span> line) {
            return new Block("heading", level, id, List.of(line));
        }
    }
}
