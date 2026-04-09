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

/** Characters pronounced ye (tone 3). */
public final class Ye3 {
    private Ye3() {}

    /** 也 (ye3) — also. */
    public static final ChineseCharacterEntry 也_ALSO = entry("也")
            .py(ZERO, I, Body.E_CARON, Tail.NONE, T3).strokes(3).radical(5)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(也_ALSO);
}
