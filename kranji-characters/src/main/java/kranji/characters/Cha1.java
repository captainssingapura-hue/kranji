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

/** Characters pronounced cha (tone 1). */
public final class Cha1 {
    private Cha1() {}

    /** 插 (cha1) — insert; plug. */
    public static final ChineseCharacterEntry 插_INSERT_PLUG = entry("插")
            .py(CH, OPEN, Body.A, Tail.NONE, T1).strokes(12).radical(64)
            .leftRight(TI_SHOU_PANG, zi("臿"))
            .phonoSemantic(TI_SHOU_PANG, zi("臿"));

    public static final List<ChineseCharacterEntry> ALL = List.of(插_INSERT_PLUG);
}
