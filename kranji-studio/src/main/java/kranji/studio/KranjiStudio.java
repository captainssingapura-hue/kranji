package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.app.StudioBrand;
import hue.captains.singapura.js.homing.studio.base.tracker.Plan;
import kranji.studio.plans.HomingIntegrationPlan;
import kranji.studio.plans.KranjiReadingPlan;

import java.util.List;

/**
 * Kranji Studio — internal tooling for building Kranji.
 *
 * <p><b>That is the line that decides what belongs here.</b> Everything in this
 * module serves the person building the project: design notes and corpus
 * reports as first-class typed artifacts rather than loose markdown, in-flight
 * work tracked with the plan kit, and workbench tools that read the model so it
 * can be checked. Nothing here ships to a reader. The reading app is the
 * product; this is the workshop, and it may look like one.</p>
 *
 * <p>The practical consequence is a different bar. A tool here earns its place
 * by answering a question that is awkward to ask of the code — which senses
 * have no example, which phrases pin a reading — not by being finished. Rough
 * is acceptable; wrong is not, because a workbench instrument that misreports
 * is worse than no instrument.</p>
 *
 * <h2>A studio depends on what it is for</h2>
 *
 * <p>This one used to depend on nothing of the model's, on the argument that it
 * documents the project rather than reading it. That was the wrong shape, and
 * it only looked right while the studio held nothing but documents. Homing
 * Studio is built with Homing; a studio standing apart from its subject is the
 * exception needing justification, not the rule.</p>
 *
 * <p>For a library, the studio is also its <b>first user</b>, and that is a
 * design service rather than a convenience: an API awkward for the studio is
 * awkward, and the studio finds out before anyone else does. Kranji is a
 * product more than a library, so that argument applies to it more weakly — but
 * it applies exactly to the tiers the tools read, and building the first
 * workbench over {@code Glosses} and {@code Examples} exercised them as a
 * consumer rather than as their author.</p>
 *
 * <p>Being a consumer means building after what it consumes, which is simply
 * what being a user costs. The invariant is not the absence of a dependency but
 * its <b>direction</b>.</p>
 *
 * <h2>The direction is the rule</h2>
 *
 * <p>The studio may depend on any part of the product. <b>No part of the
 * product may depend on the studio.</b> Maven would not object — nothing here
 * would form a cycle — so the rule is enforced by test rather than left to
 * memory: see {@code StudioDependencyDirectionTest}. A product module reaching
 * for a studio type is how internal tooling ends up shipping to a reader.</p>
 */
public record KranjiStudio() implements Studio<KranjiCatalogue> {

    public static final KranjiStudio INSTANCE = new KranjiStudio();

    @Override
    public KranjiCatalogue home() { return KranjiCatalogue.INSTANCE; }

    @Override
    public List<Plan> plans() {
        return List.of(HomingIntegrationPlan.INSTANCE, KranjiReadingPlan.INSTANCE);
    }

    @Override
    public StudioBrand standaloneBrand() {
        return new StudioBrand("Kranji · Studio", KranjiCatalogue.class);
    }
}
