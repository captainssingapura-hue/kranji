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

/** Characters pronounced lan (tone 3). */
public final class Lan3 {
    private Lan3() {}

    /** 懒 (lan3) — lazy. */
    public static final ChineseCharacterEntry 懒_LAZY = entry("懒")
            .py(L, OPEN, Body.A, Tail.N, T3).strokes(16).radical(61)
            .leftRight(SHU_XIN_PANG, zi("赖"))
            .phonoSemantic(SHU_XIN_PANG, zi("赖"));

    public static final List<ChineseCharacterEntry> ALL = List.of(懒_LAZY);
}
