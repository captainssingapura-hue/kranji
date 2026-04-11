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

/** Characters pronounced da (tone 4). */
public final class Da4 {
    private Da4() {}

    /** 大 (da4) — big. */
    public static final ChineseCharacterEntry 大_BIG = entry("大")
            .py(D, OPEN, Body.A, Tail.NONE, T4).strokes(3).radical(37)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(大_BIG);
}
