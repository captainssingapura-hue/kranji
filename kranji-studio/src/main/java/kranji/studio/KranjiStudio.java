package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.app.StudioBrand;
import hue.captains.singapura.js.homing.studio.base.tracker.Plan;
import kranji.studio.plans.HomingIntegrationPlan;
import kranji.studio.plans.KranjiReadingPlan;

import java.util.List;

/**
 * Kranji Studio — the project's own documentation and planning studio.
 *
 * <p>Kranji is a Java 21 library for modelling, classifying, and visualising
 * the internal structure of Chinese characters. This studio is where the
 * project's design notes, corpus reports, and forward-looking explorations
 * live as first-class typed artifacts rather than loose markdown, and where
 * in-flight work is tracked with the framework's plan kit.</p>
 *
 * <p>Deliberately decoupled from {@code kranji-core}: the studio documents
 * the project, it does not depend on the model. That keeps the reactor's
 * dependency graph one-directional and lets the studio build even when the
 * corpus modules are mid-refactor.</p>
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
