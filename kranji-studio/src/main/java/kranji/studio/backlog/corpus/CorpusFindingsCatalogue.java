package kranji.studio.backlog.corpus;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.backlog.BacklogCatalogue;

import java.util.List;

/**
 * What the corpus actually holds, measured rather than assumed.
 *
 * <p>The fourth kind of backlog entry, and the one that is not a complaint. A
 * Known Issue is something wrong with what we wrote; an Upstream Defect is
 * something wrong with what we depend on; an Enhancement is a capability we
 * want. A finding is <em>a fact about the corpus</em> — usually a count, or a
 * gap between what a design says and what the code does — recorded because a
 * decision later will turn on it.</p>
 *
 * <p>Findings earn a place here when they were <b>measured</b>. A number
 * somebody remembers is not a finding; a number with the query that produced
 * it is. Several of these contradict what the team believed, which is the
 * argument for writing them down rather than carrying them.</p>
 *
 * <p>A finding is not automatically work. Some are recorded precisely because
 * the decision was <em>not</em> to act — and a deferral nobody wrote down
 * becomes a rediscovery six months later.</p>
 */
public record CorpusFindingsCatalogue()
        implements L2_Catalogue<BacklogCatalogue, CorpusFindingsCatalogue>, DocProvider {

    public static final CorpusFindingsCatalogue INSTANCE = new CorpusFindingsCatalogue();

    @Override public BacklogCatalogue parent() { return BacklogCatalogue.INSTANCE; }
    @Override public String name()    { return "Corpus Findings"; }
    @Override public String summary() {
        return "Measured facts about what the corpus holds, and where the code and the "
             + "adopted design have drifted apart.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "🔬"; }   // microscope

    @Override
    public List<Entry<CorpusFindingsCatalogue>> leaves() {
        return docs().stream()
                .map(d -> Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(d.uuid().toString()), d))
                .toList();
    }

    @Override
    public List<Doc> docs() {
        return List.of(
                CorpusFinding001Doc.INSTANCE,
                CorpusFinding002Doc.INSTANCE,
                CorpusFinding003Doc.INSTANCE);
    }
}
