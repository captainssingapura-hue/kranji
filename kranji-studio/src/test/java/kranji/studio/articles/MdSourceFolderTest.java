package kranji.studio.articles;

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
 * The folder the workbench reads, and the one thing it must not do.
 *
 * <p>A studio tool that opens a path on request can read anything on the
 * machine. The folder is the permission model, so the tests that matter here
 * are the ones that try to leave it.</p>
 */
class MdSourceFolderTest {

    @AfterEach
    void clearProperty() {
        System.clearProperty(MdSourceFolder.DIR_PROPERTY);
    }

    private static void write(Path dir, String name, String body) throws IOException {
        Files.writeString(dir.resolve(name), body, StandardCharsets.UTF_8);
    }

    @Test
    void listsOnlyMarkdown(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "one.md", "# 一\n");
        write(dir, "notes.txt", "not a draft");
        write(dir, "two.md", "# 二\n");

        assertEquals(2, MdSourceFolder.drafts().size());
        assertTrue(MdSourceFolder.drafts().stream().allMatch(d -> d.name().endsWith(".md")));
    }

    @Test
    void countsCharactersRatherThanBytes(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        // Eight Han characters is 24 bytes in UTF-8. The number a person wants
        // is eight, and getting this wrong once already put a 2.6x error into
        // a design document.
        write(dir, "han.md", "一二三四五六七八");

        assertEquals(8, MdSourceFolder.drafts().get(0).chars());
    }

    @Test
    void anAbsentFolderIsEmptyRatherThanAnError(@TempDir Path dir) {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.resolve("nope").toString());

        // A workbench pointed at a folder nobody has made yet should say so on
        // screen, not fail to load.
        assertEquals(List.<MdSourceFolder.Draft>of(), MdSourceFolder.drafts());
    }

    @Test
    void aChineseNameSurvivesBecauseTheIdCarriesIt(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "地图简介.md", "# 地图\n");

        var draft = MdSourceFolder.drafts().get(0);
        assertEquals("地图简介.md", draft.name());
        assertTrue(draft.id().matches("[a-f0-9]{16}"), "an ASCII id: " + draft.id());
        assertTrue(MdSourceFolder.read(draft.id()).isPresent(),
                "the id resolves where the name could not survive a query string");
    }

    @Test
    void theIdIsStableAcrossListings(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "one.md", "# 一\n");

        assertEquals(MdSourceFolder.drafts().get(0).id(), MdSourceFolder.drafts().get(0).id());
    }

    @Test
    void nothingOutsideTheFolderCanBeRead(@TempDir Path dir) throws IOException {
        Path outside = dir.resolve("outside");
        Files.createDirectories(outside);
        write(outside, "secret.md", "# 秘密\n");

        Path inbox = dir.resolve("inbox");
        Files.createDirectories(inbox);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, inbox.toString());

        // An id is the only key, and one is only issued for what was listed.
        assertTrue(MdSourceFolder.read("../outside/secret.md").isEmpty());
        assertTrue(MdSourceFolder.read(MdSourceFolder.idOf("../outside/secret.md")).isEmpty());
        assertTrue(MdSourceFolder.read("secret.md").isEmpty());
        assertTrue(MdSourceFolder.read("").isEmpty());
        assertTrue(MdSourceFolder.read(null).isEmpty());
    }

    @Test
    void anIdForAFileThatHasGoneResolvesToNothing(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "gone.md", "# 走了\n");
        String id = MdSourceFolder.drafts().get(0).id();
        Files.delete(dir.resolve("gone.md"));

        assertFalse(MdSourceFolder.read(id).isPresent(),
                "an id is not a capability that outlives the file");
    }

}
