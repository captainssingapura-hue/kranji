package kranji.studio.backlog.enhancements;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.backlog.BacklogCatalogue;

import java.util.List;

/**
 * Wanted, understood, and not being built yet.
 *
 * <p>The third kind of backlog entry. A Known Issue is something wrong with
 * what we wrote; an Upstream Defect is something wrong with what we depend on;
 * an enhancement is nothing wrong at all — a capability we have decided we
 * want, written down while the reasoning is fresh so that picking it up later
 * does not start from a blank page.</p>
 *
 * <p>An entry earns a place here once the shape is clear enough to argue with.
 * A wish that has not been thought through belongs in a conversation.</p>
 */
public record EnhancementsCatalogue()
        implements L2_Catalogue<BacklogCatalogue, EnhancementsCatalogue>, DocProvider {

    public static final EnhancementsCatalogue INSTANCE = new EnhancementsCatalogue();

    @Override public BacklogCatalogue parent() { return BacklogCatalogue.INSTANCE; }
    @Override public String name()    { return "Enhancements"; }
    @Override public String summary() {
        return "Capabilities we want and have thought through, recorded before the reasoning "
             + "goes cold.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "🌱"; }   // 🌱

    @Override
    public List<Entry<EnhancementsCatalogue>> leaves() {
        return docs().stream()
                .map(d -> Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(d.uuid().toString()), d))
                .toList();
    }

    @Override
    public List<Doc> docs() {
        return List.of(Enhancement001Doc.INSTANCE);
    }
}
