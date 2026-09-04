package kranji.studio.plans;

import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/**
 * Section 5 — in-flight work.
 *
 * <p>Each plan is a Java file that is the single source of truth for its own
 * state: decisions with their rationale, phased tasks, and acceptance gates.
 * Edit the file, recompile, restart the server, and the tracker reflects it.
 * Git history is the change log.</p>
 */
public record PlansCatalogue()
        implements L1_Catalogue<KranjiCatalogue, PlansCatalogue> {

    public static final PlansCatalogue INSTANCE = new PlansCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Plans"; }
    @Override public String summary() {
        return "In-flight work, tracked with decisions, phases, and acceptance gates. Each "
             + "plan's Java file is its own source of truth.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "\uD83D\uDDFA"; }   // 🗺

    @Override public List<Entry<PlansCatalogue>> leaves() {
        return List.of(
                Entry.of(this, KranjiReadingPlan.INSTANCE),
                Entry.of(this, HomingIntegrationPlan.INSTANCE),
                Entry.of(this, PhonicCoveragePlan.INSTANCE),
                Entry.of(this, ContentPlan.INSTANCE),
                Entry.of(this, GlossPlan.INSTANCE),
                Entry.of(this, GlossCoveragePlan.INSTANCE)
        );
    }
}
