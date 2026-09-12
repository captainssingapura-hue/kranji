package kranji.studio.gloss;

import kranji.reading.workbench.relation.Relation;
import kranji.reading.workbench.relation.RelationSet;

import java.util.List;

/**
 * The gloss relations, answered for the shared widget.
 *
 * <p>{@link GlossRelations} stayed static and pure — it takes its registries
 * as arguments so the tests can hand it small ones — and this is the adapter
 * that binds it to the registries the studio actually holds. Every method
 * delegates; nothing is decided here.</p>
 */
public final class GlossRelationSet implements RelationSet {

    public static final GlossRelationSet INSTANCE = new GlossRelationSet();

    private GlossRelationSet() {}

    @Override public String family() { return "gloss"; }
    @Override public List<String> relations() { return GlossRelations.relations(); }
    @Override public String upstreamOf(String relation) { return GlossRelations.upstreamOf(relation); }
    @Override public String refSourceOf(String relation) { return GlossRelations.refSourceOf(relation); }
    @Override public String refTargetOf(String relation) { return GlossRelations.refTargetOf(relation); }
    @Override public List<String> columnsOf(String relation) { return GlossRelations.columnsOf(relation); }

    @Override
    public List<Relation.Row> rowsOf(String relation) {
        return GlossRelations.rowsOf(relation,
                GlossWorkbench.glosses().all(), GlossWorkbench.examples().all());
    }

    @Override
    public List<String> scopeKeysFor(String relation, List<String> keys) {
        return GlossRelations.scopeKeysFor(relation, keys);
    }

    @Override
    public List<String> refsFrom(String fromRelation, List<String> fromPks) {
        return GlossRelations.refsFrom(fromRelation, fromPks,
                GlossWorkbench.glosses().all(), GlossWorkbench.examples().all());
    }
}
