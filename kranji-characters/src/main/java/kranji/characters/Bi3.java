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

/** Characters pronounced bi (tone 3). */
public final class Bi3 {
    private Bi3() {}

    /** 比 (bi3) — compare. */
    public static final ChineseCharacterEntry 比_COMPARE = entry("比")
            .py(B, OPEN, Body.I, Tail.NONE, T3).strokes(4).radical(81)
            .leftRight(zi("匕"), zi("匕"))
            .compoundIndicative("compare");

    /** 笔 (bi3) — pen; brush. */
    public static final ChineseCharacterEntry 笔_PEN_BRUSH = entry("笔")
            .py(B, OPEN, Body.I, Tail.NONE, T3).strokes(10).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("毛"))
            .compoundIndicative("pen; brush");

    public static final List<ChineseCharacterEntry> ALL = List.of(比_COMPARE, 笔_PEN_BRUSH);
}
