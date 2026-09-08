package kranji.studio.articles;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The wire.
 *
 * <p>This is where the parser's structure meets the pane that draws it, and the
 * serialisation is written by hand — so the shape is worth pinning down. What
 * these check is that the pane can find what it needs and that nothing in a
 * draft can break out of a string.</p>
 *
 * <p>The one thing they assert about markup is that there is none. A previous
 * version of this route returned HTML, and the pane had no way to put it on
 * screen except to inject it.</p>
 */
class ArticleDraftGetActionTest {

    @AfterEach
    void clearProperty() {
        System.clearProperty(MdSourceFolder.DIR_PROPERTY);
    }

    private static String previewOf(Path dir, String body) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        Files.writeString(dir.resolve("draft.kmd"), body, StandardCharsets.UTF_8);
        return ArticleDraftGetAction.preview(MdSourceFolder.drafts().get(0).id());
    }

    @Test
    void blocksTravelAndMarkupDoesNot(@TempDir Path dir) throws IOException {
        String json = previewOf(dir, "# 标题\n\n正文。\n");

        assertTrue(json.contains("\"ok\":true"), json);
        assertTrue(json.contains("\"kind\":\"title\""), json);
        assertTrue(json.contains("\"kind\":\"p\""), json);
        assertTrue(json.contains("\"t\":\"正文。\""), json);
        assertFalse(json.contains("<p>"), "the route serves structure, never markup");
        assertFalse(json.contains("\"html\""), json);
    }

    @Test
    void aReadingAndARunReachTheOtherSide(@TempDir Path dir) throws IOException {
        String json = previewOf(dir, "# 标题\n\n地{dì}方‹**Mid**›。\n");

        assertTrue(json.contains("\"k\":\"ruby\""), json);
        assertTrue(json.contains("\"r\":\"dì\""), json);
        assertTrue(json.contains("\"e\":\"strong\""), json);
        assertTrue(json.contains("\"run\":true"), json);
    }

    @Test
    void whereTheEmphasisIsTravelsWithIt(@TempDir Path dir) throws IOException {
        // Not decoration on the wire. Bold survives over Chinese and italic does
        // not, so `run` beside `e` is what says which case a renderer is in.
        String json = previewOf(dir, "# 标题\n\n这是**重要**的。\n");

        assertTrue(json.contains("{\"k\":\"text\",\"t\":\"重要\",\"e\":\"strong\"}"), json);
    }

    @Test
    void aRefusedDraftSendsItsReasonsAndNoBlocks(@TempDir Path dir) throws IOException {
        // The failure the whole subset exists for: something was refused, so
        // nothing renders - and the pane must still be able to say why.
        String json = previewOf(dir, "# 标题\n\n| a | b |\n");

        assertTrue(json.contains("\"ok\":false"), json);
        assertTrue(json.contains("\"blocks\":[]"), json);
        assertTrue(json.contains("\"severity\":\"ERROR\""), json);
    }

    @Test
    void aDraftCannotBreakOutOfTheStringItTravelsIn(@TempDir Path dir) throws IOException {
        String json = previewOf(dir, "# 一个\"引号\"和一个\\反斜杠\n\n正文。\n");

        assertTrue(json.contains("\\\"引号\\\""), json);
        assertTrue(json.contains("\\\\反斜杠"), json);
    }

    @Test
    void anIdForNothingIsAnErrorRatherThanAnEmptyDocument(@TempDir Path dir) {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());

        String json = ArticleDraftGetAction.preview("0123456789abcdef");

        assertTrue(json.contains("\"ok\":false"), json);
        assertTrue(json.contains("no draft with id"), json);
    }

    @Test
    void theListingNamesTheFolderItRead(@TempDir Path dir) throws IOException {
        // The pane shows this. A workbench reading the wrong folder is the
        // easiest mistake to make and the hardest to notice.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        Files.writeString(dir.resolve("one.kmd"), "# 一\n", StandardCharsets.UTF_8);

        String json = ArticleDraftGetAction.listing();

        assertTrue(json.contains("\"dir\":"), json);
        assertTrue(json.contains("\"name\":\"one.kmd\""), json);
        assertTrue(json.contains("\"chars\":"), json);
    }

    @Test
    void theListingCarriesTheFolderAsATreeTheRendererCanDraw(@TempDir Path dir)
            throws IOException {
        // The folder IS the shape - no manifest, nothing to fall out of step -
        // and it arrives in the canonical TreeNode form, which is what buys the
        // bench arrow keys without anybody writing an arrow key.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        Files.createDirectories(dir.resolve("gushi/tang"));
        Files.writeString(dir.resolve("top.kmd"), "# 上\n", StandardCharsets.UTF_8);
        Files.writeString(dir.resolve("gushi/tang/deng-guan.kmd"), "# 登鹳\n",
                StandardCharsets.UTF_8);

        var tree = new JsonObject(ArticleDraftGetAction.listing()).getJsonObject("tree");

        // gushi/ nests, and the folders come before the loose files.
        var top = tree.getJsonArray("children");
        assertEquals(2, top.size(), tree.encode());
        var gushi = top.getJsonObject(0);
        assertEquals("gushi", gushi.getJsonObject("display").getString("label"));
        assertEquals("", gushi.getString("segment"), "a folder is not something to open");

        var deng = gushi.getJsonArray("children").getJsonObject(0)
                .getJsonArray("children").getJsonObject(0);
        assertEquals("deng-guan.kmd", deng.getJsonObject("display").getString("label"));
        assertFalse(deng.getString("segment").isEmpty(), "a draft's segment is its id");
        assertTrue(deng.getJsonObject("display").getString("badge").contains("characters"),
                "the count belongs in a workbench");
    }

    @Test
    void twoDraftsOfTheSameNameInDifferentFoldersStayTwo(@TempDir Path dir) throws IOException {
        // The tree is built from paths, so this is the case that would collapse
        // if anything along the way keyed on a file name.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        Files.createDirectories(dir.resolve("one"));
        Files.createDirectories(dir.resolve("two"));
        Files.writeString(dir.resolve("one/yu.kmd"), "# 甲\n", StandardCharsets.UTF_8);
        Files.writeString(dir.resolve("two/yu.kmd"), "# 乙\n", StandardCharsets.UTF_8);

        var listing = new JsonObject(ArticleDraftGetAction.listing());
        var folders = listing.getJsonObject("tree").getJsonArray("children");

        assertEquals(2, folders.size());
        String first = folders.getJsonObject(0).getJsonArray("children")
                .getJsonObject(0).getString("segment");
        String second = folders.getJsonObject(1).getJsonArray("children")
                .getJsonObject(0).getString("segment");
        assertNotEquals(first, second, "same name, different drafts");
    }
}
