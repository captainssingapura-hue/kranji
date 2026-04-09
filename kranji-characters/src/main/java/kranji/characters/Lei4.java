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

/** Characters pronounced lei (tone 4). */
public final class Lei4 {
    private Lei4() {}

    /** 类 (lei4) — kind; type. */
    public static final ChineseCharacterEntry 类_KIND_TYPE = entry("类")
            .py(L, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(9).radical(181)
            .topBottom(zi("米"), zi("大"))
            .phonoSemantic(zi("米"), zi("大"));

    /** 累 (lei4) — tired; pile up. */
    public static final ChineseCharacterEntry 累_TIRED_PILE_UP = entry("累")
            .py(L, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(11).radical(120)
            .topBottom(zi("田"), zi("糸"))
            .compoundIndicative("tired; pile up");

    /** 泪 (lei4) — tear (eye). */
    public static final ChineseCharacterEntry 泪_TEAR_EYE = entry("泪")
            .py(L, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("目"))
            .compoundIndicative("tear (eye)");

    public static final List<ChineseCharacterEntry> ALL = List.of(类_KIND_TYPE, 累_TIRED_PILE_UP, 泪_TEAR_EYE);
}
