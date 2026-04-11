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

/** Characters pronounced tian (tone 2). */
public final class Tian2 {
    private Tian2() {}

    /** 甜 (tian2) — sweet. */
    public static final ChineseCharacterEntry 甜_SWEET = entry("甜")
            .py(T, I, Body.A, Tail.N, T2).strokes(11).radical(99)
            .leftRight(zi("甘"), zi("舌"))
            .phonoSemantic(zi("甘"), zi("舌"));

    public static final List<ChineseCharacterEntry> ALL = List.of(甜_SWEET);
}
