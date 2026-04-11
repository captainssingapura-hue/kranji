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

/** Characters pronounced gu (tone 4). */
public final class Gu4 {
    private Gu4() {}

    /** 故 (gu4) — reason; old. */
    public static final ChineseCharacterEntry 故_REASON_OLD = entry("故")
            .py(G, U, Body.U, Tail.NONE, T4).strokes(9).radical(66)
            .leftRight(zi("古"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("古"));

    /** 顾 (gu4) — look after. */
    public static final ChineseCharacterEntry 顾_LOOK_AFTER = entry("顾")
            .py(G, U, Body.U, Tail.NONE, T4).strokes(10).radical(181)
            .leftRight(zi("雇"), zi("页"))
            .phonoSemantic(zi("页"), zi("雇"));

    /** 固 (gu4) — solid; fixed. */
    public static final ChineseCharacterEntry 固_SOLID_FIXED = entry("固")
            .py(G, U, Body.U, Tail.NONE, T4).strokes(8).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("古"))
            .phonoSemantic(GUO_ZI_KUANG, zi("古"));

    public static final List<ChineseCharacterEntry> ALL = List.of(故_REASON_OLD, 顾_LOOK_AFTER, 固_SOLID_FIXED);
}
