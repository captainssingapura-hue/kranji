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

/** Characters pronounced shou (tone 3). */
public final class Shou3 {
    private Shou3() {}

    /** 手 (shou3) — hand. */
    public static final ChineseCharacterEntry 手_HAND = entry("手")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(4).radical(64)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(手_HAND);
}
