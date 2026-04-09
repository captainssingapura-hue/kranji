package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced guang (tone 3). */
public final class Guang3 {
    private Guang3() {}

    /** 广 (guang3) — wide; broad. */
    public static final ChineseCharacterEntry 广_WIDE_BROAD = entry("广")
            .py(G, U, Body.A, Tail.NG, T3).strokes(3).radical(53)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(广_WIDE_BROAD);
}
