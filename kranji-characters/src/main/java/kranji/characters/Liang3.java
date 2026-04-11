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

/** Characters pronounced liang (tone 3). */
public final class Liang3 {
    private Liang3() {}

    /** 两 (liang3) — two; pair. */
    public static final ChineseCharacterEntry 两_TWO_PAIR = entry("两")
            .py(L, I, Body.A, Tail.NG, T3).strokes(7).radical(1)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(两_TWO_PAIR);
}
