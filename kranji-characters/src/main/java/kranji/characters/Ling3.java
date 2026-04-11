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

/** Characters pronounced ling (tone 3). */
public final class Ling3 {
    private Ling3() {}

    /** 领 (ling3) — lead; collar. */
    public static final ChineseCharacterEntry 领_LEAD_COLLAR = entry("领")
            .py(L, OPEN, Body.I, Tail.NG, T3).strokes(8).radical(181)
            .leftRight(zi("令"), zi("页"))
            .phonoSemantic(zi("页"), zi("令"));

    public static final List<ChineseCharacterEntry> ALL = List.of(领_LEAD_COLLAR);
}
