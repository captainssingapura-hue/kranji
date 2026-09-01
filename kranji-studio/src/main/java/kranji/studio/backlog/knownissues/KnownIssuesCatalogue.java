package kranji.studio.backlog.knownissues;

import hue.captains.singapura.js.homing.studio.base.Doc;
import hue.captains.singapura.js.homing.studio.base.DocProvider;
import hue.captains.singapura.js.homing.studio.base.app.DocReader;
import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L2_Catalogue;
import kranji.studio.backlog.BacklogCatalogue;

import java.util.List;

/**
 * Numbered known issues.
 *
 * <p>Each is something measured or verified rather than suspected, recorded
 * with what it would take to fix and what it costs to leave. Numbering lets an
 * issue be cited from a plan, a commit, or a review.</p>
 *
 * <p>An issue lives here when it is real, understood, and deliberately not
 * being worked on. Something actively being fixed belongs to a plan phase;
 * something merely suspected belongs in a conversation until it is checked.</p>
 */
public record KnownIssuesCatalogue()
        implements L2_Catalogue<BacklogCatalogue, KnownIssuesCatalogue>, DocProvider {

    public static final KnownIssuesCatalogue INSTANCE = new KnownIssuesCatalogue();

    @Override public BacklogCatalogue parent() { return BacklogCatalogue.INSTANCE; }
    @Override public String name()    { return "Known Issues"; }
    @Override public String summary() {
        return "Verified defects and hazards, numbered - what it is, what it costs to leave, "
             + "and what would fix it.";
    }
    @Override public String badge()   { return "SECTION"; }
    @Override public String icon()    { return "🐛"; }   // 🐛

    @Override
    public List<Entry<KnownIssuesCatalogue>> leaves() {
        return docs().stream()
                .map(d -> Entry.of(this, DocReader.INSTANCE,
                        new DocReader.Params(d.uuid().toString()), d))
                .toList();
    }

    @Override
    public List<Doc> docs() {
        return List.of(
                KnownIssue001Doc.INSTANCE,
                KnownIssue002Doc.INSTANCE,
                KnownIssue003Doc.INSTANCE,
                KnownIssue004Doc.INSTANCE,
                KnownIssue005Doc.INSTANCE,
                KnownIssue006Doc.INSTANCE,
                KnownIssue007Doc.INSTANCE,
                KnownIssue008Doc.INSTANCE,
                KnownIssue009Doc.INSTANCE);
    }
}
