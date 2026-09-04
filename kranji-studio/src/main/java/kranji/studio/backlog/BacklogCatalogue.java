package kranji.studio.backlog;

import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L1_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.backlog.corpus.CorpusFindingsCatalogue;
import kranji.studio.backlog.enhancements.EnhancementsCatalogue;
import kranji.studio.backlog.upstream.UpstreamDefectsCatalogue;
import kranji.studio.KranjiCatalogue;
import kranji.studio.backlog.knownissues.KnownIssuesCatalogue;

import java.util.List;

/**
 * Work that is known about but not scheduled.
 *
 * <p>Distinct from Plans, which track work in flight with phases and gates.
 * The backlog holds what has been found and deliberately not acted on yet —
 * so that "we know" is written down somewhere other than a conversation.</p>
 *
 * <p>Four kinds, divided by what the entry is <em>about</em>: a Known Issue is
 * something wrong with what we wrote, an Upstream Defect something wrong with
 * what we depend on, an Enhancement a capability we want and have thought
 * through. A Corpus Finding is none of those — it is a measured fact, recorded
 * because a later decision turns on it, and often because the decision was not
 * to act.</p>
 */
public record BacklogCatalogue()
        implements L1_Catalogue<KranjiCatalogue, BacklogCatalogue> {

    public static final BacklogCatalogue INSTANCE = new BacklogCatalogue();

    @Override public KranjiCatalogue parent() { return KranjiCatalogue.INSTANCE; }
    @Override public String name()    { return "Backlog"; }
    @Override public String summary() {
        return "Known but unscheduled - issues found and deliberately deferred, recorded so "
             + "the deferral is a decision rather than a memory.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "📋"; }   // 📋

    @Override public List<Entry<BacklogCatalogue>> leaves() {
        return List.of();
    }

    @Override
    public List<? extends L2_Catalogue<BacklogCatalogue, ?>> subCatalogues() {
        return List.of(KnownIssuesCatalogue.INSTANCE, UpstreamDefectsCatalogue.INSTANCE,
                       EnhancementsCatalogue.INSTANCE, CorpusFindingsCatalogue.INSTANCE);
    }
}
