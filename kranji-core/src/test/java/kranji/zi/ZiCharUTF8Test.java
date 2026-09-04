package kranji.zi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZiCharUTF8Test {

    private static final ZiCharUTF8Codec CODEC = ZiCharUTF8Codec.INSTANCE;

    // ── The reason this type exists ────────────────────────────────────

    @Test
    void holdsCharactersOutsideTheBasicMultilingualPlane() {
        // 𰻝 biáng, U+30EDD — two Java chars, one character. A char-based type
        // could not represent it, and eight such characters are already in the
        // corpus.
        ZiCharUTF8 biang = new ZiCharUTF8(0x30EDD);

        assertTrue(biang.isSupplementary());
        assertEquals(2, biang.value().length(), "two UTF-16 code units");
        assertEquals(1, biang.value().codePointCount(0, biang.value().length()),
                "but exactly one character");
        assertEquals("U+30EDD", biang.codePointLabel());
    }

    @Test
    void roundTripsASupplementaryCharacterThroughString() {
        ZiCharUTF8 biang = new ZiCharUTF8(0x30EDD);
        assertEquals(biang, CODEC.from(CODEC.to(biang)));
    }

    // ── Admission ──────────────────────────────────────────────────────

    @Test
    void acceptsUnifiedIdeographsAndRadicalSupplement() {
        assertTrue(ZiCharUTF8.isHan(0x6E05), "清 — CJK Unified Ideographs");
        assertTrue(ZiCharUTF8.isHan(0x2E8C), "⺌ — CJK Radicals Supplement, used for bound parts");
        assertTrue(ZiCharUTF8.isHan(0x20087), "Extension B");
    }

    @Test
    void rejectsNonHan() {
        assertFalse(ZiCharUTF8.isHan('A'));
        assertFalse(ZiCharUTF8.isHan('1'));
        assertFalse(ZiCharUTF8.isHan(0x3042), "あ — Hiragana");
        assertFalse(ZiCharUTF8.isHan(0x2FF0),
                "⿰ — an ideographic description operator, not a character");
        assertThrows(IllegalArgumentException.class, () -> new ZiCharUTF8('A'));
    }

    @Test
    void rejectsAnInvalidCodePoint() {
        assertThrows(IllegalArgumentException.class, () -> new ZiCharUTF8(0x110000));
        assertThrows(IllegalArgumentException.class, () -> new ZiCharUTF8(-1));
    }

    // ── Codec: throwing versus lenient ─────────────────────────────────

    @Test
    void fromThrowsOnAnythingButOneCharacter() {
        assertThrows(IllegalArgumentException.class, () -> CODEC.from(""));
        assertThrows(IllegalArgumentException.class, () -> CODEC.from("好好"));
        assertThrows(IllegalArgumentException.class, () -> CODEC.from("A"));
        assertThrows(NullPointerException.class, () -> CODEC.from(null));
    }

    @Test
    void tryFromReturnsEmptyRatherThanThrowing() {
        assertTrue(CODEC.tryFrom("好").isPresent());
        assertTrue(CODEC.tryFrom("").isEmpty());
        assertTrue(CODEC.tryFrom(null).isEmpty());
        assertTrue(CODEC.tryFrom("好好").isEmpty());
        assertTrue(CODEC.tryFrom("，").isEmpty(), "punctuation is not a character we model");
    }

    // ── Value semantics ────────────────────────────────────────────────

    @Test
    void equalityAndOrderingAreByCodePoint() {
        assertEquals(CODEC.from("清"), new ZiCharUTF8(0x6E05));
        assertTrue(new ZiCharUTF8(0x4E00).compareTo(new ZiCharUTF8(0x6E05)) < 0);
    }

    @Test
    void toStringIsTheCharacterItself() {
        assertEquals("清", CODEC.from("清").toString());
    }
}
