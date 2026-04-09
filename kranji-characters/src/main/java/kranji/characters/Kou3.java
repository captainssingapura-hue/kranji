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

/** Characters pronounced kou (tone 3). */
public final class Kou3 {
    private Kou3() {}

    /** 口 (kou) — mouth. Singular pictograph. */
    public static final ChineseCharacterEntry 口_MOUTH = entry("口")
            .py(K, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(3).radical(30)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(口_MOUTH);
}
