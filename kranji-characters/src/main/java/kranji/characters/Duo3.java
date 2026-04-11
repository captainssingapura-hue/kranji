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

/** Characters pronounced duo (tone 3). */
public final class Duo3 {
    private Duo3() {}

    /** 朵 (duo3) — flower (mw). */
    public static final ChineseCharacterEntry 朵_FLOWER_MW = entry("朵")
            .py(D, U, Body.O, Tail.NONE, T3).strokes(6).radical(75)
            .topBottom(zi("几"), MU)
            .compoundIndicative("flower (mw)");

    public static final List<ChineseCharacterEntry> ALL = List.of(朵_FLOWER_MW);
}
