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

/** Characters pronounced hei (tone 1). */
public final class Hei1 {
    private Hei1() {}

    /** 黑 (hei1) — black. */
    public static final ChineseCharacterEntry 黑_BLACK = entry("黑")
            .py(H, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(12).radical(203)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(黑_BLACK);
}
