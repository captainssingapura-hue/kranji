package kranji.studio.articles;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The shelf.
 *
 * <p>Two things are being asserted and they pull in opposite directions. The
 * shelf has to be <b>widenable</b> — that is the whole feature — and it has to
 * stay the only way in, so that a path arriving from a browser is still not a
 * way to read the machine.</p>
 *
 * <p>Every test names its own store. A test that read the real one would pass
 * or fail on whoever ran it.</p>
 */
class ArticleRootsTest {

    @AfterEach
    void clearProperties() {
        System.clearProperty(ArticleRoots.STORE_PROPERTY);
        System.clearProperty(MdSourceFolder.DIR_PROPERTY);
    }

    private static Path storeIn(Path dir) {
        Path store = dir.resolve("shelf.json");
        System.setProperty(ArticleRoots.STORE_PROPERTY, store.toString());
        return store;
    }

    private static Path folder(Path dir, String name) throws IOException {
        return Files.createDirectories(dir.resolve(name));
    }

    private static List<String> names() {
        return ArticleRoots.roots().stream().map(ArticleRoots.Root::name).toList();
    }

    // ── Before anything has been configured ────────────────────────────

    @Test
    void withNoStoreTheShelfIsWhateverTheLaunchCommandSaid(@TempDir Path dir) throws IOException {
        // A studio started the old way opens on the folder it was given, so
        // nothing has to be configured before anything works.
        storeIn(dir);
        Path inbox = folder(dir, "articles-inbox");
        System.setProperty(MdSourceFolder.DIR_PROPERTY, inbox.toString());

        assertEquals(List.of("articles-inbox"), names());
    }

    @Test
    void readingTheShelfDoesNotWriteIt(@TempDir Path dir) throws IOException {
        // A studio that wrote a config file merely because somebody opened a
        // pane would be deciding something on their behalf.
        Path store = storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());

        ArticleRoots.roots();

        assertFalse(Files.exists(store), "the seed is not a decision");
    }

    @Test
    void anEmptyShelfIsEmptyRatherThanAnError(@TempDir Path dir) {
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.resolve("nope").toString());

        assertEquals(List.of(), ArticleRoots.roots());
        assertTrue(ArticleRoots.first().isEmpty());
    }

    // ── Adding and removing ────────────────────────────────────────────

    @Test
    void addingAFolderPutsItOnTheShelfAndWritesItDown(@TempDir Path dir) throws IOException {
        Path store = storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Path gushi = folder(dir, "gushi");

        assertTrue(ArticleRoots.add(gushi.toString()).ok());

        assertEquals(List.of("inbox", "gushi"), names(), "the seed came with it");
        assertTrue(Files.exists(store), "the first change writes the file");
        var written = new JsonObject(Files.readString(store, StandardCharsets.UTF_8));
        assertEquals(2, written.getJsonArray("roots").size());
    }

    @Test
    void aPathThatIsNotAFolderIsRefusedRatherThanAdded(@TempDir Path dir) throws IOException {
        // A root that lists nothing looks exactly like a root whose drafts have
        // all been deleted, and the difference matters at the moment somebody
        // is wondering where their files went.
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Files.writeString(dir.resolve("a-file.kmd"), "# 一\n", StandardCharsets.UTF_8);

        ArticleRoots.Change refused = ArticleRoots.add(dir.resolve("a-file.kmd").toString());
        assertFalse(refused.ok());
        assertTrue(refused.message().contains("is not a folder"), refused.message());

        assertFalse(ArticleRoots.add("").ok(), "nothing typed");
        assertFalse(ArticleRoots.add(dir.resolve("ghost").toString()).ok(), "not there");
        assertEquals(List.of("inbox"), names(), "and none of that changed the shelf");
    }

    @Test
    void thereIsNoPointAddingTheSameFolderTwice(@TempDir Path dir) throws IOException {
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Path gushi = folder(dir, "gushi");

        assertTrue(ArticleRoots.add(gushi.toString()).ok());
        ArticleRoots.Change again = ArticleRoots.add(gushi.toString());

        assertFalse(again.ok());
        assertTrue(again.message().contains("already a root"), again.message());
        assertEquals(2, ArticleRoots.roots().size());
    }

    @Test
    void removingTakesItOffTheShelfAndLeavesTheFolderAlone(@TempDir Path dir) throws IOException {
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Path gushi = folder(dir, "gushi");
        ArticleRoots.add(gushi.toString());

        String id = ArticleRoots.roots().get(1).id();
        assertTrue(ArticleRoots.remove(id).ok());

        assertEquals(List.of("inbox"), names());
        assertTrue(Files.isDirectory(gushi), "this is a list, not a bin");
    }

    @Test
    void aRootWhoseFolderHasGoneIsShownRatherThanDropped(@TempDir Path dir) throws IOException {
        // A folder on a drive that is not mounted is still a root somebody
        // chose. Losing it from the list would be the tool losing their work.
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Path gone = folder(dir, "gone");
        ArticleRoots.add(gone.toString());
        Files.delete(gone);

        List<ArticleRoots.Root> roots = ArticleRoots.roots();
        assertEquals(2, roots.size());
        assertFalse(roots.get(1).present());
    }

    // ── The shelf is still the only way in ─────────────────────────────

    @Test
    void aFolderNobodyAddedResolvesToNothingHoweverItIsAskedFor(@TempDir Path dir)
            throws IOException {
        // The lock that survives the shelf being widenable. A root is addressed
        // by an id the server issued, so a path from a query string is not a
        // way to read the machine - it is not an id at all.
        storeIn(dir);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());
        Path secret = folder(dir, "secret");

        assertTrue(ArticleRoots.dirOf(secret.toString()).isEmpty(), "a path is not an id");
        assertTrue(ArticleRoots.dirOf(ArticleRoots.idOf(secret.toString())).isEmpty(),
                "and neither is the id it would have had, until somebody adds it");
        assertTrue(ArticleRoots.dirOf("").isEmpty());
        assertTrue(ArticleRoots.dirOf(null).isEmpty());

        ArticleRoots.add(secret.toString());
        assertTrue(ArticleRoots.dirOf(ArticleRoots.idOf(secret.toString())).isPresent(),
                "and it does resolve once it has been put on the shelf deliberately");
    }

    @Test
    void aStoreNobodyCanParseFallsBackRatherThanStoppingTheStudio(@TempDir Path dir)
            throws IOException {
        // Hand-edited into nonsense. A pane that would not open is a worse
        // answer than one that opens on the folder it was launched with.
        Path store = storeIn(dir);
        Files.writeString(store, "not json at all", StandardCharsets.UTF_8);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, folder(dir, "inbox").toString());

        assertEquals(List.of("inbox"), names());
    }
}
