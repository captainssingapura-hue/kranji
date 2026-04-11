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

/** Characters pronounced lan (tone 2). */
public final class Lan2 {
    private Lan2() {}

    /** 兰 (lan2) — orchid. */
    public static final ChineseCharacterEntry 兰_ORCHID = entry("兰")
            .py(L, OPEN, Body.A, Tail.N, T2).strokes(5).radical(140)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(兰_ORCHID);
}
