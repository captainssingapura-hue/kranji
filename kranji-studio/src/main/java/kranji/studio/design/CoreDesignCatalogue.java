package kranji.studio.design;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import kranji.studio.KranjiCatalogue;

import java.util.List;

/**
 * Core design decisions, numbered and durable.
 *
 * <p>Distinct from Architecture, which describes what the code currently does.
 * These record the load-bearing shape decisions — the ones that would be
 * expensive to reverse and that later work is expected to respect. Each is
 * numbered so it can be cited from a plan, a commit message, or a review
 * without ambiguity.</p>
 *
 * <p>A core design entry states the decision, why it holds, and — importantly —
 * where it strains. A decision recorded without its limits invites being
 * applied where it does not fit.</p>
 */
public record CoreDesignCatalogue()
        implements L1_Catalogue<KranjiCatalogue, CoreDesignCatalogue>, DocProvider {

    public static final CoreDesignCatalogue INSTANCE = new CoreDesignCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Core Design"; }
    @Override public String summary() {
        return "Numbered decisions about the shape of the system - what the load-bearing "
             + "structures are, why they hold, and where they strain.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "\uD83E\uDDF1"; }   // 🧱

    @Override public List<Entry<CoreDesignCatalogue>> leaves() {
        return List.of(
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(CoreDesign001Doc.INSTANCE.uuid().toString()),
                        CoreDesign001Doc.INSTANCE),
                Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(CoreDesign002Doc.INSTANCE.uuid().toString()),
                        CoreDesign002Doc.INSTANCE)
        );
    }

    @Override public List<Doc> docs() {
        return List.of(CoreDesign001Doc.INSTANCE, CoreDesign002Doc.INSTANCE);
    }
}
