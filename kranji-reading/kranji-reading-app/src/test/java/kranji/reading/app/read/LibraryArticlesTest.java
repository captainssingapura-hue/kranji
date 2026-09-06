package kranji.reading.app.read;

import kranji.library.testkit.LibraryArticlesTestBase;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;

import java.util.List;

/**
 * Every article of every root on the classpath, one case each.
 *
 * <h2>Why this survives the per-group tests</h2>
 *
 * <p>Each library module now runs {@link LibraryArticlesTestBase} over its own
 * tree, which is where an author wants the failure. This is not that check
 * repeated: it is the only module where every discovered root is present at
 * once - the published library and the demonstration set together - so it is
 * the only place the question can be asked of the arrangement a reader
 * actually gets.</p>
 *
 * <p>It also catches what a group cannot see from inside itself: an article
 * that parses against its own module's classpath and not against the one the
 * application assembles.</p>
 *
 * <p>The warning report this writes, {@code target/article-warnings.txt}, is
 * the whole-library one that {@code kranji-library/README.md} sends authors to.
 * Each group module now writes its own alongside it, holding only that group's
 * articles.</p>
 */
class LibraryArticlesTest extends LibraryArticlesTestBase {

    @Override
    protected List<LibraryTree> trees() {
        return Libraries.discovered().stream().map(ArticleLibrary::tree).toList();
    }

    /**
     * The libraries not loading is the failure this number exists for: a
     * classpath with no content jar yields no cases, and no cases would
     * otherwise read as a pass.
     */
    @Override
    protected int fewestArticlesExpected() { return 100; }
}
