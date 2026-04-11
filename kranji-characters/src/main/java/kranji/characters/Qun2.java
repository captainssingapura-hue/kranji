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

/** Characters pronounced qun (tone 2). */
public final class Qun2 {
    private Qun2() {}

    /** 裙 (qun2) — skirt. */
    public static final ChineseCharacterEntry 裙_SKIRT = entry("裙")
            .py(Q, V, Body.E, Tail.N, T2).strokes(12).radical(145)
            .leftRight(YI_ZI_PANG, zi("君"))
            .phonoSemantic(YI_ZI_PANG, zi("君"));

    public static final List<ChineseCharacterEntry> ALL = List.of(裙_SKIRT);
}
