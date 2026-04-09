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

/** Characters pronounced ba (tone 1). */
public final class Ba1 {
    private Ba1() {}

    /** 八 (ba1) — eight. */
    public static final ChineseCharacterEntry 八_EIGHT = entry("八")
            .py(B, OPEN, Body.A, Tail.NONE, T1).strokes(2).radical(12)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(八_EIGHT);
}
