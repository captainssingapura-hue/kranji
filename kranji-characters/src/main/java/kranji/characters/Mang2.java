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

/** Characters pronounced mang (tone 2). */
public final class Mang2 {
    private Mang2() {}

    /** 忙 (mang2) — busy. */
    public static final ChineseCharacterEntry 忙_BUSY = entry("忙")
            .py(M, OPEN, Body.A, Tail.NG, T2).strokes(6).radical(61)
            .leftRight(SHU_XIN_PANG, zi("亡"))
            .phonoSemantic(SHU_XIN_PANG, zi("亡"));

    public static final List<ChineseCharacterEntry> ALL = List.of(忙_BUSY);
}
