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

/** Characters pronounced cai (tone 3). */
public final class Cai3 {
    private Cai3() {}

    /** 彩 (cai3) — color; variety. */
    public static final ChineseCharacterEntry 彩_COLOR_VARIETY = entry("彩")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(11).radical(59)
            .leftRight(zi("采"), zi("彡"))
            .phonoSemantic(zi("彡"), zi("采"));

    /** 采 (cai3) — pick; gather. */
    public static final ChineseCharacterEntry 采_PICK_GATHER = entry("采")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(8).radical(165)
            .topBottom(zi("爫"), MU)
            .compoundIndicative("pick; gather");

    /** 踩 (cai3) — step on; trample. */
    public static final ChineseCharacterEntry 踩_STEP_ON_TRAMPLE = entry("踩")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(15).radical(157)
            .leftRight(zi("足"), zi("采"))
            .phonoSemantic(zi("足"), zi("采"));

    public static final List<ChineseCharacterEntry> ALL = List.of(彩_COLOR_VARIETY, 采_PICK_GATHER, 踩_STEP_ON_TRAMPLE);
}
