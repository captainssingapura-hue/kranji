package kranji.library.xiaohua;

import kranji.library.testkit.LibraryArticlesTestBase;
import kranji.reading.library.LibraryTree;

import java.util.List;

/**
 * Every 笑话 article reads, one case each.
 *
 * <p>The check lives in {@link LibraryArticlesTestBase}; this names the tree
 * it should be run over. It runs here, in the module being edited, rather
 * than only at the end of the reactor.</p>
 */
class ArticlesTest extends LibraryArticlesTestBase {

    @Override
    protected List<LibraryTree> trees() { return List.of(XiaoHuaLibrary.TREE); }

    /** A whole shelf falling off the tree would otherwise just be fewer cases. */
    @Override
    protected int fewestArticlesExpected() { return 40; }
}
