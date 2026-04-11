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

/** Characters pronounced shen (tone 2). */
public final class Shen2 {
    private Shen2() {}

    /** 什 (shen2) — what. */
    public static final ChineseCharacterEntry 什_WHAT = entry("什")
            .py(SH, OPEN, Body.E, Tail.N, T2).strokes(4).radical(9)
            .leftRight(DAN_REN_PANG, zi("十"))
            .phonoSemantic(DAN_REN_PANG, zi("十"));

    /** 神 (shen2) — god; spirit. */
    public static final ChineseCharacterEntry 神_GOD_SPIRIT = entry("神")
            .py(SH, OPEN, Body.E, Tail.N, T2).strokes(9).radical(113)
            .leftRight(SHI_ZI_PANG_SPIRIT, zi("申"))
            .phonoSemantic(SHI_ZI_PANG_SPIRIT, zi("申"));

    public static final List<ChineseCharacterEntry> ALL = List.of(什_WHAT, 神_GOD_SPIRIT);
}
