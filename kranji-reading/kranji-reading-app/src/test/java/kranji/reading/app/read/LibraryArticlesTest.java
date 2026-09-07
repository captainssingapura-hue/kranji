package kranji.reading.app.read;

import kranji.reading.testkit.LibraryArticlesTestBase;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;

import java.util.List;

/**
 * Every article of every root on this classpath, one case each.
 *
 * <h2>What this can still see, now the collections have moved out</h2>
 *
 * <p>The published library lives in its own repository and reaches a reader as
 * a second jar, discovered through {@code META-INF/services}. It is not on this
 * module's classpath and this test can no longer speak for it — that check
 * belongs to the build that produces it, where {@link LibraryArticlesTestBase}
 * now runs per group.</p>
 *
 * <p>What is left here is the demonstration set, and it is worth keeping: it is
 * what a reader gets when no collections jar is present, so it is the library
 * every deployment has. An article that will not parse in it is a broken page
 * in the default install.</p>
 *
 * <p>The wider question — that the app and a collections jar agree once they
 * are put together — cannot be asked in either repository alone. It is a
 * property of the pair, and the place to ask it is whatever assembles them.</p>
 */
class LibraryArticlesTest extends LibraryArticlesTestBase {

    @Override
    protected List<LibraryTree> trees() {
        return Libraries.discovered().stream().map(ArticleLibrary::tree).toList();
    }

    /**
     * The demonstration set, and no fewer.
     *
     * <p>The number exists for the case where nothing loads at all: a classpath
     * with no library on it yields no cases, and no cases would otherwise read
     * as a pass. It was 100 while the published library was here; it is the
     * demo set's size now, and a drop means the fallback library is missing
     * rather than that a collections jar is absent.</p>
     */
    @Override
    protected int fewestArticlesExpected() { return 20; }
}
