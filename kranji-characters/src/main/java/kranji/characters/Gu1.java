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

/** Characters pronounced gu (tone 1). */
public final class Gu1 {
    private Gu1() {}

    /** 估 (gu1) — estimate. */
    public static final ChineseCharacterEntry 估_ESTIMATE = entry("估")
            .py(G, U, Body.U, Tail.NONE, T1).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("古"))
            .phonoSemantic(DAN_REN_PANG, zi("古"));

    /** 孤 (gu1) — alone; orphan. */
    public static final ChineseCharacterEntry 孤_ALONE_ORPHAN = entry("孤")
            .py(G, U, Body.U, Tail.NONE, T1).strokes(8).radical(39)
            .leftRight(zi("孑"), zi("瓜"))
            .phonoSemantic(zi("孑"), zi("瓜"));

    public static final List<ChineseCharacterEntry> ALL = List.of(估_ESTIMATE, 孤_ALONE_ORPHAN);
}
