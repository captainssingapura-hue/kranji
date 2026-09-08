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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The folder the workbench reads, and the one thing it must not do.
 *
 * <p>A studio tool that opens a path on request can read anything on the
 * machine. The folder is the permission model, so the tests that matter here
 * are the ones that try to leave it.</p>
 *
 * <p>The rest are about the folder being a <i>tree</i>: the directory structure
 * is the shape the editor shows, so where a draft sits has to survive the trip
 * and two drafts with the same name in different folders have to stay two.</p>
 */
class MdSourceFolderTest {

    @AfterEach
    void clearProperty() {
        System.clearProperty(MdSourceFolder.DIR_PROPERTY);
    }

    /** Writes at a path relative to the folder, making parents as needed. */
    private static void write(Path dir, String path, String body) throws IOException {
        Path file = dir.resolve(path);
        Files.createDirectories(file.getParent());
        Files.writeString(file, body, StandardCharsets.UTF_8);
    }

    private static List<String> paths() {
        return MdSourceFolder.drafts().stream().map(MdSourceFolder.Draft::path).toList();
    }

    @Test
    void listsOnlyKranjiMarkdown(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "one.kmd", "# 一\n");
        write(dir, "notes.txt", "not a draft");
        // .md is not .kmd on purpose: what these hold is not CommonMark, and a
        // file that says it is would be read wrong by everything downstream.
        write(dir, "plain.md", "# 二\n");

        assertEquals(List.of("one.kmd"), paths());
    }

    @Test
    void countsCharactersRatherThanBytes(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        // Eight Han characters is 24 bytes in UTF-8. The number a person wants
        // is eight, and getting this wrong once already put a 2.6x error into
        // a design document.
        write(dir, "han.kmd", "一二三四五六七八");

        assertEquals(8, MdSourceFolder.drafts().get(0).chars());
    }

    @Test
    void anAbsentFolderIsEmptyRatherThanAnError(@TempDir Path dir) {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.resolve("nope").toString());

        // A workbench pointed at a folder nobody has made yet should say so on
        // screen, not fail to load.
        assertEquals(List.<MdSourceFolder.Draft>of(), MdSourceFolder.drafts());
    }

    // ── The folder is a tree ───────────────────────────────────────────

    @Test
    void draftsAreFoundInSubfoldersAndSayWhereTheySit(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "top.kmd", "# 上\n");
        write(dir, "gushi/yu.kmd", "# 雨\n");
        write(dir, "gushi/tang/deng-guan.kmd", "# 登鹳\n");

        assertEquals(List.of("gushi/tang/deng-guan.kmd", "gushi/yu.kmd", "top.kmd"), paths());
        assertEquals("deng-guan.kmd", MdSourceFolder.drafts().get(0).name(), "the label");
    }

    @Test
    void aPathIsWrittenWithForwardSlashesWhateverThePlatformUses(@TempDir Path dir)
            throws IOException {
        // The id is a hash of the path, so a backslash here would give the same
        // file two different ids on two different machines.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "gushi/tang/deng-guan.kmd", "# 登鹳\n");

        String path = MdSourceFolder.drafts().get(0).path();
        assertEquals("gushi/tang/deng-guan.kmd", path);
        assertFalse(path.contains("\\"), path);
    }

    @Test
    void theSameNameInTwoFoldersIsTwoDrafts(@TempDir Path dir) throws IOException {
        // The reason the id hashes the path and not the name. Hashing the name
        // would have collapsed these into one draft opening whichever was
        // listed first.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "one/yu.kmd", "# 甲的雨\n");
        write(dir, "two/yu.kmd", "# 乙的雨\n");

        List<MdSourceFolder.Draft> drafts = MdSourceFolder.drafts();
        assertEquals(2, drafts.size());
        assertNotEquals(drafts.get(0).id(), drafts.get(1).id());
        assertTrue(MdSourceFolder.read(drafts.get(0).id()).orElseThrow().contains("甲"));
        assertTrue(MdSourceFolder.read(drafts.get(1).id()).orElseThrow().contains("乙"));
    }

    @Test
    void aHiddenFolderIsNotWalked(@TempDir Path dir) throws IOException {
        // Somebody will point this at a checkout eventually.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "kept.kmd", "# 留\n");
        write(dir, ".git/objects/stray.kmd", "# 不要\n");

        assertEquals(List.of("kept.kmd"), paths());
    }

    @Test
    void draftsComeBackInPathOrderRatherThanByTime(@TempDir Path dir) throws IOException {
        // A tree that reorders itself when you save is one nobody can point at.
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "b.kmd", "# 乙\n");
        write(dir, "a.kmd", "# 甲\n");

        assertEquals(List.of("a.kmd", "b.kmd"), paths());
    }

    // ── Names, ids, and the folder as the permission model ─────────────

    @Test
    void aChineseNameSurvivesBecauseTheIdCarriesIt(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "地图/地图简介.kmd", "# 地图\n");

        var draft = MdSourceFolder.drafts().get(0);
        assertEquals("地图简介.kmd", draft.name());
        assertEquals("地图/地图简介.kmd", draft.path());
        assertTrue(draft.id().matches("[a-f0-9]{16}"), "an ASCII id: " + draft.id());
        assertTrue(MdSourceFolder.read(draft.id()).isPresent(),
                "the id resolves where the name could not survive a query string");
    }

    @Test
    void theIdIsStableAcrossListings(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "one.kmd", "# 一\n");

        assertEquals(MdSourceFolder.drafts().get(0).id(), MdSourceFolder.drafts().get(0).id());
    }

    @Test
    void nothingOutsideTheFolderCanBeRead(@TempDir Path dir) throws IOException {
        Path outside = dir.resolve("outside");
        Files.createDirectories(outside);
        Files.writeString(outside.resolve("secret.kmd"), "# 秘密\n", StandardCharsets.UTF_8);

        Path inbox = dir.resolve("inbox");
        Files.createDirectories(inbox);
        System.setProperty(MdSourceFolder.DIR_PROPERTY, inbox.toString());

        // An id is the only key, and one is only issued for what was listed.
        assertTrue(MdSourceFolder.read("../outside/secret.kmd").isEmpty());
        assertTrue(MdSourceFolder.read(MdSourceFolder.idOf("../outside/secret.kmd")).isEmpty());
        assertTrue(MdSourceFolder.read("secret.kmd").isEmpty());
        assertTrue(MdSourceFolder.read("").isEmpty());
        assertTrue(MdSourceFolder.read(null).isEmpty());
    }

    @Test
    void anIdForAFileThatHasGoneResolvesToNothing(@TempDir Path dir) throws IOException {
        System.setProperty(MdSourceFolder.DIR_PROPERTY, dir.toString());
        write(dir, "gone/gone.kmd", "# 走了\n");
        String id = MdSourceFolder.drafts().get(0).id();
        Files.delete(dir.resolve("gone/gone.kmd"));

        assertFalse(MdSourceFolder.read(id).isPresent(),
                "an id is not a capability that outlives the file");
    }
}
