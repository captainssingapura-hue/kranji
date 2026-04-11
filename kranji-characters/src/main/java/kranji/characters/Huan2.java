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

/** Characters pronounced huan (tone 2). */
public final class Huan2 {
    private Huan2() {}

    /** 环 (huan2) — ring; surround. */
    public static final ChineseCharacterEntry 环_RING_SURROUND = entry("环")
            .py(H, U, Body.A, Tail.N, T2).strokes(8).radical(96)
            .leftRight(zi("王"), zi("不"))
            .phonoSemantic(zi("王"), zi("不"));

    public static final List<ChineseCharacterEntry> ALL = List.of(环_RING_SURROUND);
}
