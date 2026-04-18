package kranji.codegen.singulars;

import kranji.json.dto.SingularZiJson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaEmitterTest {

    /** Minimal shape — glyph + meaning + pinyin + strokes + library. */
    @Test
    void emits_basic_record_shape() {
        SingularZiJson data = new SingularZiJson(
                "\u738B", "U+738B", null,
                4, null,
                "w\u00E1ng",
                null, "king", null);

        String src = JavaEmitter.emitSingularZi(
                "kranji.singular.people.w", "Wang_King", data);

        // Banner and package/imports
        assertTrue(src.startsWith(JavaEmitter.AUTO_BANNER + "\n"),
                "auto banner on first line");
        assertTrue(src.contains("\npackage kranji.singular.people.w;\n"));
        assertTrue(src.contains("import kranji.library.BasicSet;"));
        assertTrue(src.contains("import kranji.library.LibraryMember;"));
        assertTrue(src.contains("import kranji.zi.SingularZi;"));

        // Record + singleton
        assertTrue(src.contains(
                "public record Wang_King() implements LibraryMember<BasicSet>, SingularZi {"));
        assertTrue(src.contains(
                "public static final Wang_King INSTANCE = new Wang_King();"));

        // Overrides
        assertTrue(src.contains(
                "@Override public String glyph() { return \"\\u738B\"; }"));
        assertTrue(src.contains(
                "@Override public String meaning() { return \"king\"; }"));
        assertTrue(src.contains(
                "@Override public String pinyinText() { return \"w\\u00E1ng\"; }"));
        assertTrue(src.contains(
                "@Override public int strokes() { return 4; }"));
        assertTrue(src.contains(
                "@Override public BasicSet library() { return BasicSet.INSTANCE; }"));

        // name defaults to glyph — not emitted
        assertFalse(src.contains("public String name()"),
                "name override should be absent when it equals glyph");
        // radicalNo is null — not emitted
        assertFalse(src.contains("public int radicalNo()"),
                "radicalNo override should be absent when null");
    }

    /** Non-null radicalNo is emitted. */
    @Test
    void emits_radicalNo_when_present() {
        SingularZiJson data = new SingularZiJson(
                "\u6C34", "U+6C34", null,
                4, 85,
                "shu\u01D0",
                null, "water", null);

        String src = JavaEmitter.emitSingularZi(
                "kranji.singular.nature.sh", "Shui_Water", data);

        assertTrue(src.contains(
                "@Override public int radicalNo() { return 85; }"));
    }

    /** Non-default name is emitted. */
    @Test
    void emits_name_when_different_from_glyph() {
        SingularZiJson data = new SingularZiJson(
                "\u6C35", "U+6C35", "\u4E09\u70B9\u6C34",  // 氵, 三点水
                3, null,
                "shu\u01D0",
                null, "water (left form)", null);

        String src = JavaEmitter.emitSingularZi(
                "kranji.singular.radicals.sh", "Shui_ThreeDots", data);

        // name="三点水" → must appear as an escaped \\u sequence
        assertTrue(src.contains(
                "@Override public String name() { return \"\\u4E09\\u70B9\\u6C34\"; }"),
                "name override with Unicode escape");
    }

    /** Empty / zero defaults are suppressed. */
    @Test
    void suppresses_default_values() {
        SingularZiJson data = new SingularZiJson(
                "\u6728", null, null,
                0, null,
                "",       // default pinyinText
                null,
                "",       // default meaning
                null);

        String src = JavaEmitter.emitSingularZi(
                "kranji.singular.plants.m", "Mu_Tree", data);

        assertFalse(src.contains("public String meaning()"));
        assertFalse(src.contains("public String pinyinText()"));
        assertFalse(src.contains("public int strokes()"));
    }

    @Test
    void rejects_missing_glyph() {
        SingularZiJson data = new SingularZiJson(
                "", null, null, 1, null, null, null, null, null);

        assertThrows(IllegalArgumentException.class,
                () -> JavaEmitter.emitSingularZi("p", "X", data));
    }

    /** quoteUnicode escapes non-ASCII but leaves printable ASCII alone. */
    @Test
    void quoteUnicode_escapes_non_ascii() {
        assertEquals("\"abc\"", JavaEmitter.quoteUnicode("abc"));
        assertEquals("\"\\u738B\"", JavaEmitter.quoteUnicode("\u738B"));
        // escape of quote + backslash
        assertEquals("\"a\\\"b\\\\c\"", JavaEmitter.quoteUnicode("a\"b\\c"));
    }

    /** quoteJava keeps non-ASCII verbatim — for the meaning field. */
    @Test
    void quoteJava_keeps_non_ascii_verbatim() {
        assertEquals("\"king\"", JavaEmitter.quoteJava("king"));
        assertEquals("\"a\\\"b\"", JavaEmitter.quoteJava("a\"b"));
    }
}
