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

/** Characters pronounced jin (tone 4). */
public final class Jin4 {
    private Jin4() {}

    /** 进 (jin4) — enter; advance. */
    public static final ChineseCharacterEntry 进_ENTER_ADVANCE = entry("进")
            .py(J, OPEN, Body.I, Tail.N, T4).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("井"))
            .phonoSemantic(ZOU_ZHI_DI, zi("井"));

    /** 近 (jin4) — near; close. */
    public static final ChineseCharacterEntry 近_NEAR_CLOSE = entry("近")
            .py(J, OPEN, Body.I, Tail.N, T4).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("斤"))
            .phonoSemantic(ZOU_ZHI_DI, zi("斤"));

    /** 尽 (jin4) — exhaust; all. */
    public static final ChineseCharacterEntry 尽_EXHAUST_ALL = entry("尽")
            .py(J, OPEN, Body.I, Tail.N, T4).strokes(6).radical(44)
            .singular()
            .compoundIndicative("exhaust; all");

    public static final List<ChineseCharacterEntry> ALL = List.of(进_ENTER_ADVANCE, 近_NEAR_CLOSE, 尽_EXHAUST_ALL);
}
