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

/** Characters pronounced gou (tone 1). */
public final class Gou1 {
    private Gou1() {}

    /** 钩 (gou1) — hook. */
    public static final ChineseCharacterEntry 钩_HOOK = entry("钩")
            .py(G, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(9).radical(167)
            .leftRight(JIN_ZI_PANG, zi("勾"))
            .phonoSemantic(JIN_ZI_PANG, zi("勾"));

    public static final List<ChineseCharacterEntry> ALL = List.of(钩_HOOK);
}
