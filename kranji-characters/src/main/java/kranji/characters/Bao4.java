package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced bao (tone 4). */
public final class Bao4 {
    private Bao4() {}

    /** 报 (bao4) — report; news. */
    public static final ChineseCharacterEntry 报_REPORT_NEWS = entry("报")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("卩"))
            .phonoSemantic(TI_SHOU_PANG, zi("卩"));

    /** 抱 (bao4) — hug; hold. */
    public static final ChineseCharacterEntry 抱_HUG_HOLD = entry("抱")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("包"))
            .phonoSemantic(TI_SHOU_PANG, zi("包"));

    public static final List<ChineseCharacterEntry> ALL = List.of(报_REPORT_NEWS, 抱_HUG_HOLD);
}
