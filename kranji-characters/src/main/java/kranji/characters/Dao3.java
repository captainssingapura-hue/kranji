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

/** Characters pronounced dao (tone 3). */
public final class Dao3 {
    private Dao3() {}

    /** 导 (dao3) — guide; lead. */
    public static final ChineseCharacterEntry 导_GUIDE_LEAD = entry("导")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(6).radical(41)
            .topBottom(zi("巳"), zi("寸"))
            .phonoSemantic(zi("寸"), zi("道"));

    /** 倒 (dao3) — fall; pour. */
    public static final ChineseCharacterEntry 倒_FALL_POUR = entry("倒")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(10).radical(9)
            .leftRight(DAN_REN_PANG, zi("到"))
            .phonoSemantic(DAN_REN_PANG, zi("到"));

    /** 岛 (dao3) — island. */
    public static final ChineseCharacterEntry 岛_ISLAND = entry("岛")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(7).radical(46)
            .topBottom(zi("鸟"), zi("山"))
            .compoundIndicative("island");

    public static final List<ChineseCharacterEntry> ALL = List.of(导_GUIDE_LEAD, 倒_FALL_POUR, 岛_ISLAND);
}
