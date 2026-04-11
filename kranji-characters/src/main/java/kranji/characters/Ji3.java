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

/** Characters pronounced ji (tone 3). */
public final class Ji3 {
    private Ji3() {}

    /** 己 (ji) — self. Singular pictograph. */
    public static final ChineseCharacterEntry 己_SELF = entry("己")
            .py(J, OPEN, Body.I, Tail.NONE, T3).strokes(3).radical(49)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(己_SELF);
}
