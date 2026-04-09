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

/** Characters pronounced cao (tone 3). */
public final class Cao3 {
    private Cao3() {}

    /** 草 (cao3) — grass. */
    public static final ChineseCharacterEntry 草_GRASS = entry("草")
            .py(C, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(9).radical(140)
            .topBottom(CAO_ZI_TOU, zi("早"))
            .phonoSemantic(CAO_ZI_TOU, zi("早"));

    public static final List<ChineseCharacterEntry> ALL = List.of(草_GRASS);
}
