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

/** Characters pronounced shui (tone 3). */
public final class Shui3 {
    private Shui3() {}

    /** 水 (shui) — water. Singular pictograph. */
    public static final ChineseCharacterEntry 水_WATER = entry("水")
            .py(SH, U, Body.E, Tail.VOWEL_I, T3).strokes(4).radical(85)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(水_WATER);
}
