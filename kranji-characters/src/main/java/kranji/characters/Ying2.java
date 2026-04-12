package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import kranji.component.HintedZi;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced ying (tone 2). */
public final class Ying2 {
    private Ying2() {}

    /** 赢 (ying2) — win. */
    public static final ChineseCharacterEntry 赢_WIN = entry("赢")
            .py(ZERO, OPEN, Body.I, Tail.NG, T2).strokes(17).radical(154)
            .topMiddleBottom(HintedZi.WANG, HintedZi.KOU, lmr(zi("月"), zi("贝"), zi("凡")))
            .compoundIndicative("win");

    public static final List<ChineseCharacterEntry> ALL = List.of(赢_WIN);
}
