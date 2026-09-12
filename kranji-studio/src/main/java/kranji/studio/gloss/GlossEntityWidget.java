package kranji.studio.gloss;

import kranji.reading.workbench.relation.RelationEntityWidget;
import kranji.reading.workbench.relation.RelationSet;

/**
 * One relation of the gloss model, on a grid, wired into the gloss cascade.
 *
 * <p>The grid, the filter, the cascade and the bus are
 * {@link RelationEntityWidget}'s — they were this class's, and moved to the
 * library bench so a second family of relations could use them. What is left
 * here is the four answers that make a relation widget a <em>gloss</em>
 * relation widget: which family, which route, which party.</p>
 */
public abstract class GlossEntityWidget<SELF extends GlossEntityWidget<SELF>>
        extends RelationEntityWidget<SELF> {

    /** The party as declared in the workspace specs that mount these grids. */
    public static final String PARTY = "glossSelection";

    /** The party as the grids find it on {@code workspaceCtx}. */
    public static final String PARTY_EXPOSED = "glossParty";

    @Override protected RelationSet relations() { return GlossRelationSet.INSTANCE; }
    @Override protected String route() { return GlossRelationGetAction.PATH; }
    @Override protected String secretaryName() { return PARTY; }
    @Override protected String partyExposedName() { return PARTY_EXPOSED; }
}
