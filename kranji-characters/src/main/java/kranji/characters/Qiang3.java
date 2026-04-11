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

/** Characters pronounced qiang (tone 3). */
public final class Qiang3 {
    private Qiang3() {}

    /** 抢 (qiang3) — grab; rob. */
    public static final ChineseCharacterEntry 抢_GRAB_ROB = entry("抢")
            .py(Q, I, Body.A, Tail.NG, T3).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("仓"))
            .phonoSemantic(TI_SHOU_PANG, zi("仓"));

    public static final List<ChineseCharacterEntry> ALL = List.of(抢_GRAB_ROB);
}
