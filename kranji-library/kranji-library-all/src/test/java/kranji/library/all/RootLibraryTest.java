package kranji.library.all;

import kranji.library.gushi.GuShiLibrary;
import kranji.library.kepu.KePuLibrary;
import kranji.library.kouyu.KouYuLibrary;
import kranji.library.shici.ShiCiLibrary;
import kranji.library.xiaohua.XiaoHuaLibrary;
import kranji.library.zhinan.ZhiNanLibrary;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The root grafts every group, once, and is the one that gets mounted.
 *
 * <p>Grafting is the fault this file exists for. Six trees written apart and
 * joined here can lose a group silently — nothing fails to compile when a
 * branch is left out of the root — and can collide, because two jars released
 * separately have no way to see each other's collection ids.</p>
 */
class RootLibraryTest {

    private static final LibraryTree ROOT = KranjiLibrary.INSTANCE.tree();

    @Test
    void everyGroupIsGrafted() {
        var grafted = ROOT.collections().stream().map(c -> c.id().toString()).toList();
        for (LibraryTree group : List.of(ShiCiLibrary.TREE, GuShiLibrary.TREE,
                KePuLibrary.TREE, XiaoHuaLibrary.TREE, KouYuLibrary.TREE,
                ZhiNanLibrary.TREE)) {
            for (ArticleCollection c : group.collections()) {
                assertTrue(grafted.contains(c.id().toString()),
                        group.title() + " holds " + c.title()
                        + ", which the root does not reach");
            }
        }
    }

    @Test
    void theTreeIsWellFormed() {
        // Collection ids unique across all five jars, and article ids unique
        // within each - the two collisions that grafting makes possible and
        // that no single module can rule out on its own.
        ROOT.validate();
    }

    @Test
    void everyArticleResolvesToAResource() {
        var missing = new ArrayList<String>();
        for (ArticleCollection c : ROOT.collections()) {
            for (ArticleRef ref : c.articles()) {
                try (InputStream in = getClass().getResourceAsStream(ref.resource())) {
                    if (in == null) missing.add(ref.title() + " -> " + ref.resource());
                } catch (Exception e) {
                    missing.add(ref.title() + " -> " + e);
                }
            }
        }
        assertEquals(List.of(), missing, "articles with no text: " + missing);
    }

    @Test
    void noTwoArticlesShareAResource() {
        // Across jars this time. The demo library and kranji-library-kepu both
        // wrote /articles/kepu/shui.txt once, and on one classpath a reader
        // would have got whichever jar sorted first.
        var seen = new LinkedHashSet<String>();
        var repeated = new ArrayList<String>();
        for (ArticleCollection c : ROOT.collections()) {
            for (ArticleRef ref : c.articles()) {
                if (!seen.add(ref.resource())) repeated.add(ref.resource());
            }
        }
        assertEquals(List.of(), repeated, "one text mounted twice: " + repeated);
    }

    @Test
    void theRootIsWhatDiscoveryMounts() {
        // This module's own classpath holds one root, so discovery has nothing
        // to weigh. The interesting case - this root beside the demo one - is
        // where both jars meet, in kranji-reading-app.
        List<ArticleLibrary> found = Libraries.discovered();
        assertEquals(List.of("kranji"), found.stream().map(ArticleLibrary::name).toList());
        assertSame(ROOT, Libraries.mounted().tree());
    }

    @Test
    void theArrangementIsTheOneWritten() {
        assertEquals("读物", ROOT.title());
        var top = ((LibraryTree.Branch) ROOT).children().stream().map(LibraryTree::title).toList();
        assertEquals(List.of("使用指南", "文学", "科普读物", "语言游戏"), top);
        assertFalse(ROOT.collections().isEmpty());
    }
}
