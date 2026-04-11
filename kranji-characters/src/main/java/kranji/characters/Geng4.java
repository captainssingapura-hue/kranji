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

/** Characters pronounced geng (tone 4). */
public final class Geng4 {
    private Geng4() {}

    /** 更 (geng4) — more; change. */
    public static final ChineseCharacterEntry 更_MORE_CHANGE = entry("更")
            .py(G, OPEN, Body.E, Tail.NG, T4).strokes(7).radical(73)
            .singular()
            .compoundIndicative("more; change");

    public static final List<ChineseCharacterEntry> ALL = List.of(更_MORE_CHANGE);
}
