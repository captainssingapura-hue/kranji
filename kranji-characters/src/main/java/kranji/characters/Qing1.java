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

/** Characters pronounced qing (tone 1). */
public final class Qing1 {
    private Qing1() {}

    /** 清 (qing) — clear. LeftRight: 氵 + 青. Phono-semantic. */
    public static final ChineseCharacterEntry 清_CLEAR = entry("清")
            .py(Q, OPEN, Body.I, Tail.NG, T1).strokes(11).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("青"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("青"));

    public static final List<ChineseCharacterEntry> ALL = List.of(清_CLEAR);
}
