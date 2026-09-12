package kranji.reading.app.gloss;

import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;
import kranji.reading.testkit.GlossCoverageTestBase;

import java.util.List;

/**
 * How much of the library on this classpath the shipped meanings explain.
 *
 * <p>On this module's classpath that is the demonstration set — 23 articles —
 * measured against the three gloss collections {@code kranji-dist} deploys,
 * which sit here at runtime scope for exactly this reason. It is the smallest
 * honest measurement: the fallback library every install has, against the
 * meanings every install has.</p>
 *
 * <p>The published library is measured where it is assembled, in
 * {@code kranji-reading-collections}; the base class is the same so the two
 * reports read alike.</p>
 *
 * <p>The floor is under what the demo set gets today. It is not a target for
 * the demo set — nobody is going to gloss for 23 articles — it is a tripwire
 * for the case where a gloss jar falls off this classpath, which would take
 * this number to zero and would otherwise be noticed by a child.</p>
 */
class GlossCoverageTest extends GlossCoverageTestBase {

    @Override
    protected List<LibraryTree> trees() {
        return Libraries.discovered().stream().map(ArticleLibrary::tree).toList();
    }

    @Override
    protected String library() { return Libraries.mounted().name(); }

    @Override
    protected double floor() { return 0.50; }
}
