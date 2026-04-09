package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced zhou (tone 1). */
public final class Zhou1 {
    private Zhou1() {}

    /** 州 (zhou1) — state; province. */
    public static final ChineseCharacterEntry 州_STATE_PROVINCE = entry("州")
            .py(ZH, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(6).radical(47)
            .singular()
            .pictograph();

    /** 周 (zhou1) — week; circle. */
    public static final ChineseCharacterEntry 周_WEEK_CIRCLE = entry("周")
            .py(ZH, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(8).radical(30)
            .singular()
            .compoundIndicative("week; circle");

    /** 洲 (zhou1) — continent. */
    public static final ChineseCharacterEntry 洲_CONTINENT = entry("洲")
            .py(ZH, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("州"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("州"));

    public static final List<ChineseCharacterEntry> ALL = List.of(州_STATE_PROVINCE, 周_WEEK_CIRCLE, 洲_CONTINENT);
}
