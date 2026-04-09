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

/** Characters pronounced dao (tone 1). */
public final class Dao1 {
    private Dao1() {}

    /** 刀 (dao1) — knife; blade. */
    public static final ChineseCharacterEntry 刀_KNIFE_BLADE = entry("刀")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(2).radical(18)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(刀_KNIFE_BLADE);
}
