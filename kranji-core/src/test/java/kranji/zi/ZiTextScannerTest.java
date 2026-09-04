package kranji.zi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZiTextScannerTest {

    private static final ZiTextScanner SCANNER = ZiTextScanner.INSTANCE;

    private static List<String> values(List<ZiCharUTF8> zis) {
        return zis.stream().map(ZiCharUTF8::value).toList();
    }

    // ── The failure this class exists to prevent ───────────────────────

    @Test
    void countsASupplementaryCharacterOnceRatherThanAsTwoHalves() {
        // 𰻝 U+30EDD is two UTF-16 code units. A char-by-char scan would emit
        // two broken halves, or silently drop it.
        List<ZiCharUTF8> found = SCANNER.scan("一𰻝二");

        assertEquals(3, found.size());
        assertEquals(List.of("一", "𰻝", "二"), values(found));
        assertTrue(found.get(1).isSupplementary());
    }

    // ── What is kept and what is dropped ───────────────────────────────

    @Test
    void keepsHanAndDropsEverythingElse() {
        assertEquals(List.of("他", "去", "银", "行"),
                values(SCANNER.scan("他去银行, ok? 123")),
                "punctuation, spacing, Latin and digits are not characters we model");
    }

    @Test
    void dropsIdeographicPunctuationAndDescriptionOperators() {
        // 。and ，are COMMON script; ⿰ and ⿱ are description operators, not
        // characters. All four must be excluded.
        assertEquals(List.of("好", "冷"),
                values(SCANNER.scan("好，冷。⿰⿱")));
    }

    @Test
    void keepsRadicalsWhichAreHan() {
        // ⺌ CJK Radicals Supplement, ⼀ Kangxi Radicals — both HAN, and both
        // appear as bound parts in the corpus.
        assertEquals(2, SCANNER.scan("⺌⼀").size());
    }

    // ── scan versus distinct ───────────────────────────────────────────

    @Test
    void scanKeepsRepeatsBecauseReadabilityCountsOccurrences() {
        assertEquals(List.of("好", "好", "好"), values(SCANNER.scan("好好好")));
    }

    @Test
    void distinctCollapsesRepeatsInOrderOfFirstAppearance() {
        assertEquals(List.of("我", "爱", "的", "国"),
                values(SCANNER.distinct("我爱我的国, 我爱国")));
    }

    @Test
    void distinguishesManyRepeatsFromManyDifferentCharacters() {
        String repeated = "好好好好好好";
        String varied   = "好冷天气很坏";

        assertEquals(SCANNER.scan(repeated).size(), SCANNER.scan(varied).size(),
                "the same number of occurrences");
        assertEquals(1, SCANNER.distinct(repeated).size());
        assertEquals(6, SCANNER.distinct(varied).size(),
                "but a very different reading task");
    }

    // ── Edges ──────────────────────────────────────────────────────────

    @Test
    void emptyAndNullYieldEmpty() {
        assertEquals(List.of(), SCANNER.scan(""));
        assertEquals(List.of(), SCANNER.scan(null));
        assertEquals(List.of(), SCANNER.distinct(""));
        assertEquals(List.of(), SCANNER.distinct(null));
    }

    @Test
    void textWithNoHanYieldsEmpty() {
        assertEquals(List.of(), SCANNER.scan("hello, world!"));
    }
}
