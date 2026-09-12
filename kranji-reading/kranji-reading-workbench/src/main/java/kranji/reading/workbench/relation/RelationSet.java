package kranji.reading.workbench.relation;

import java.util.ArrayList;
import java.util.List;

/**
 * A family of relations a workbench shows as linked grids.
 *
 * <h2>What a set declares</h2>
 *
 * <p>Which relations there are, how each is keyed to the one above it, what
 * columns it shows, and its rows. That is enough for {@link RelationEntityWidget}
 * to draw any of them and for the cascade to work between them: a widget
 * hears a selection on its upstream relation, asks for its own rows under
 * those keys, takes the first, and announces it. Nothing in the widget knows
 * the shape of the chain — the shape is here.</p>
 *
 * <h2>Why an interface and not a switch</h2>
 *
 * <p>The studio's gloss workbench grew as one static class with a switch per
 * question, and that was right while it was the only family. The library
 * bench has a second family — coverage — and cannot reach the studio's; the
 * two share a widget, a wire format and a selection bus, and the only thing
 * that differs is the answers to these six questions. So the questions are
 * the interface, and each family answers them for itself.</p>
 */
public interface RelationSet {

    /**
     * A short lowercase name for the family — {@code gloss}, {@code coverage}.
     *
     * <p>Prefixes actor ids and grid labels so two families on one page cannot
     * be confused by a person reading a log, and cannot collide on the bus.</p>
     */
    String family();

    /** The relations, in the order a picker should offer them. */
    List<String> relations();

    /** Which relation feeds this one. A root answers {@code null}. */
    String upstreamOf(String relation);

    /** The columns, in display order. Empty for a relation this set does not have. */
    List<String> columnsOf(String relation);

    /** Every row of a relation, unscoped, each carrying its own key and its parent's. */
    List<Relation.Row> rowsOf(String relation);

    /**
     * The relation whose rows' {@link Relation.Row#refs()} land on this one, if any.
     *
     * <p>Containment is one edge; reference is another. Most families have
     * only the first.</p>
     */
    default String refSourceOf(String relation) { return null; }

    /** The inverse of {@link #refSourceOf}: what this relation's refs name. */
    default String refTargetOf(String relation) { return null; }

    /**
     * A sentence to show above a relation's rows, or {@code null}.
     *
     * <p>For the one row set in a family whose headline no row states — a
     * whole-library figure above a per-shelf grid.</p>
     */
    default String noteOf(String relation) { return null; }

    /**
     * The keys a relation actually scopes on, given what was selected above it.
     *
     * <p>Identity for almost every relation. The exception is one whose rows
     * belong to a coarser grain than its upstream's key — the studio's problem
     * relation belongs to a character while its upstream is keyed on a pair —
     * and narrowing here keeps that decision testable in Java rather than
     * trapped in the browser.</p>
     */
    default List<String> scopeKeysFor(String relation, List<String> keys) { return keys; }

    /**
     * Follow the refs of some rows to the keys they name.
     *
     * <p>Resolved here rather than by taking a key apart in the browser, so how
     * one relation's key is composed never becomes a parsing contract for
     * another.</p>
     */
    default List<String> refsFrom(String fromRelation, List<String> fromPks) {
        var out = new ArrayList<String>();
        for (Relation.Row row : rowsOf(fromRelation)) {
            if (!fromPks.contains(row.pk())) continue;
            for (String ref : row.refs()) if (!out.contains(ref)) out.add(ref);
        }
        return List.copyOf(out);
    }
}
