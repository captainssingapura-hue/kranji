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

/** Characters pronounced leng (tone 3). */
public final class Leng3 {
    private Leng3() {}

    /** 冷 (leng3) — cold. */
    public static final ChineseCharacterEntry 冷_COLD = entry("冷")
            .py(L, OPEN, Body.E, Tail.NG, T3).strokes(7).radical(15)
            .leftRight(LIANG_DIAN_SHUI, zi("令"))
            .phonoSemantic(LIANG_DIAN_SHUI, zi("令"));

    public static final List<ChineseCharacterEntry> ALL = List.of(冷_COLD);
}
