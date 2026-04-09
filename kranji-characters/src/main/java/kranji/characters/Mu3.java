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

/** Characters pronounced mu (tone 3). */
public final class Mu3 {
    private Mu3() {}

    /** 母 (mu3) — mother. */
    public static final ChineseCharacterEntry 母_MOTHER = entry("母")
            .py(M, U, Body.U, Tail.NONE, T3).strokes(5).radical(80)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(母_MOTHER);
}
