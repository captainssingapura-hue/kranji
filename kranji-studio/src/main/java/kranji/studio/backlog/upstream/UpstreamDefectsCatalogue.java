package kranji.studio.backlog.upstream;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.backlog.BacklogCatalogue;

import java.util.List;

/**
 * Defects in things we depend on rather than in what we wrote.
 *
 * <p>Separate from Known Issues because the remedy is different. A known issue
 * is ours to fix when we choose to; one of these is a report to somebody else,
 * and what we carry meanwhile is a workaround that should be removed when the
 * defect is.</p>
 *
 * <p>Each entry therefore records three things a Known Issue does not: what the
 * upstream code actually does, what we are doing instead, and what would let us
 * stop.</p>
 */
public record UpstreamDefectsCatalogue()
        implements L2_Catalogue<BacklogCatalogue, UpstreamDefectsCatalogue>, DocProvider {

    public static final UpstreamDefectsCatalogue INSTANCE = new UpstreamDefectsCatalogue();

    @Override public BacklogCatalogue parent() { return BacklogCatalogue.INSTANCE; }
    @Override public String name()    { return "Upstream Defects"; }
    @Override public String summary() {
        return "Defects in dependencies, with the workaround we carry and the change that "
             + "would let us drop it.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "🧩"; }   // 🧩

    @Override
    public List<Entry<UpstreamDefectsCatalogue>> leaves() {
        return docs().stream()
                .map(d -> Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(d.uuid().toString()), d))
                .toList();
    }

    @Override
    public List<Doc> docs() {
        return List.of(UpstreamDefect001Doc.INSTANCE, UpstreamDefect002Doc.INSTANCE,
                       UpstreamDefect003Doc.INSTANCE);
    }
}
