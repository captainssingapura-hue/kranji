package kranji.reading.app.read;

import kranji.reading.content.DemoLibrary;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleEntry;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.ArticleUmbrella;
import kranji.reading.library.Classifier;
import kranji.reading.library.CollectionId;
import kranji.reading.library.LibraryTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * How the tree draws a work with more than one telling.
 *
 * <p>The JSON is asserted on as text rather than parsed, deliberately: the
 * renderer on the other side reads exactly these strings, and a test that
 * parsed them first would pass on JSON the renderer could not.</p>
 */
class ArticleTreeGetActionTest {

    private record Grouped(CollectionId id, String title, List<? extends ArticleEntry> entries)
            implements ArticleCollection {
        @Override public List<ArticleRef> articles() {
            var out = new ArrayList<ArticleRef>();
            for (ArticleEntry e : entries) out.addAll(e.articles());
            return List.copyOf(out);
        }
    }

    private static final ArticleCollection SHELF = new Grouped(
            CollectionId.named("t.chengyu"), "成语故事", List.of(
                    ArticleRef.of("hua-she-tian-zu", "画蛇添足", "/t/hstz.txt"),
                    ArticleUmbrella.of("ke-zhou-qiu-jian", "刻舟求剑", Map.of(
                            new Classifier.Original(),
                            ArticleRef.by("ke-zhou-qiu-jian-yuanwen", "刻舟求剑", "吕氏春秋", "/t/kzqj-y.txt"),
                            new Classifier.Retold(1),
                            ArticleRef.of("ke-zhou-qiu-jian", "刻舟求剑", "/t/kzqj.txt")))));

    private static final String JSON = ArticleTreeGetAction.treeJson(
            LibraryTree.of("读物", LibraryTree.shelf(SHELF)));

    @Test
    void aSoloArticleIsDrawnExactlyAsBefore() {
        // The shape every one of the 476 published articles is drawn with.
        // Nothing about it may move: the renderer keys on kind, the widget
        // reads the address out of the segment.
        assertTrue(JSON.contains(
                "\"segment\":\"t.chengyu:hua-she-tian-zu\",\"display\":{\"label\":\"画蛇添足(huà shé tiān zú)\","
              + "\"badge\":\"\",\"note\":\"\",\"kind\":\"article\"}"), JSON);
    }

    @Test
    void anUmbrellaIsANodeAndNotAnArticle() {
        // Its segment is its own slug, not an address, and its kind is not
        // 'article' - so the widget's open() ignores it and activation expands
        // it rather than trying to read a work.
        assertTrue(JSON.contains(
                "\"segment\":\"ke-zhou-qiu-jian\",\"display\":{\"label\":\"刻舟求剑(kè zhōu qiú jiàn)\","
              + "\"badge\":\"\",\"note\":\"\",\"kind\":\"work\"},\"dimensions\":[],\"children\":["), JSON);
    }

    @Test
    void editionsHangUnderTheWorkLabelledByWhatDistinguishesThem() {
        // Not the title - all editions share it - but 白话 and 原文, sounded
        // like everything else, with the author where an article's author
        // goes. Retelling first, however the map was declared.
        int retold = JSON.indexOf(
                "\"segment\":\"t.chengyu:ke-zhou-qiu-jian\",\"display\":{\"label\":\"白话(bái huà)\","
              + "\"badge\":\"\",\"note\":\"\",\"kind\":\"article\"}");
        int original = JSON.indexOf(
                "\"segment\":\"t.chengyu:ke-zhou-qiu-jian-yuanwen\",\"display\":{\"label\":\"原文(yuán wén)\","
              + "\"badge\":\"\",\"note\":\"吕氏春秋(lǚ shì chūn qiū)\",\"kind\":\"article\"}");

        assertTrue(retold > 0, "no retelling row: " + JSON);
        assertTrue(original > 0, "no original row: " + JSON);
        assertTrue(retold < original, "the retelling must come first");
    }

    @Test
    void theEditionsAreOneLevelBelowTheWork() {
        int work = JSON.indexOf("\"segment\":\"ke-zhou-qiu-jian\"");
        String levelOfWork = JSON.substring(JSON.lastIndexOf("\"level\":\"L", work), work);
        int edition = JSON.indexOf("\"segment\":\"t.chengyu:ke-zhou-qiu-jian\"");
        String levelOfEdition = JSON.substring(JSON.lastIndexOf("\"level\":\"L", edition), edition);

        assertEquals("\"level\":\"L2\",", levelOfWork);
        assertEquals("\"level\":\"L3\",", levelOfEdition);
    }

    @Test
    void theCountIsOfTellingsNotOfEntries() {
        // Three things to read on this shelf, listed as two. The badge says
        // what a reader can open, which is what "to read" has always meant.
        assertTrue(JSON.contains("\"badge\":\"3\",\"note\":\"3 to read\""), JSON);
    }

    @Test
    void theDemoLibraryDrawsItsTwoUmbrellas() {
        // The shipped example of the feature. If someone flattens it back
        // into two shelves, this is the test that notices.
        String demo = ArticleTreeGetAction.treeJson(DemoLibrary.INSTANCE.tree());

        assertTrue(demo.contains("\"segment\":\"ke-zhou-qiu-jian\"") && demo.contains("\"kind\":\"work\""), demo);
        assertTrue(demo.contains("\"segment\":\"kranji.reader.demo.chengyu:ke-zhou-qiu-jian-yuanwen\""), demo);
        assertTrue(demo.contains("\"segment\":\"kranji.reader.demo.yuyan:shou-zhu-dai-tu-yuanwen\""), demo);
        // And the originals are no longer on the 文言 shelf.
        assertFalse(demo.contains("kranji.reader.demo.wenyan:ke-zhou-qiu-jian\""), demo);
    }
}
