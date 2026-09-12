package kranji.reading.workbench.coverage;

import kranji.reading.workbench.relation.RelationEntityWidget;
import kranji.reading.workbench.relation.RelationSet;

/** A coverage relation on a grid: the family's route and bus, fixed once. */
public abstract class CoverageWidget<SELF extends CoverageWidget<SELF>>
        extends RelationEntityWidget<SELF> {

    /** The party as the workspace spec declares it. */
    public static final String PARTY = "coverageSelection";

    /** The party as widgets find it on {@code workspaceCtx}. */
    public static final String PARTY_EXPOSED = "coverageParty";

    @Override protected RelationSet relations() { return CoverageRelations.INSTANCE; }
    @Override protected String route() { return CoverageRelationGetAction.PATH; }
    @Override protected String secretaryName() { return PARTY; }
    @Override protected String partyExposedName() { return PARTY_EXPOSED; }
}
