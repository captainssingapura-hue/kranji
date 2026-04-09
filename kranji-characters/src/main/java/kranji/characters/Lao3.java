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

/** Characters pronounced lao (tone 3). */
public final class Lao3 {
    private Lao3() {}

    /** 老 (lao3) — old. */
    public static final ChineseCharacterEntry 老_OLD = entry("老")
            .py(L, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(6).radical(125)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(老_OLD);
}
