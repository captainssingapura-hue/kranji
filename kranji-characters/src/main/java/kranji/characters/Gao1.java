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

/** Characters pronounced gao (tone 1). */
public final class Gao1 {
    private Gao1() {}

    /** 高 (gao1) — high; tall. */
    public static final ChineseCharacterEntry 高_HIGH_TALL = entry("高")
            .py(G, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(10).radical(189)
            .topBottom(WEN_ZI_TOU, zi("口冂口"))
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(高_HIGH_TALL);
}
