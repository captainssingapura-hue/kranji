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

/** Characters pronounced si (tone 3). */
public final class Si3 {
    private Si3() {}

    /** 死 (si3) — die; death. */
    public static final ChineseCharacterEntry 死_DIE_DEATH = entry("死")
            .py(S, OPEN, Body.NULL, Tail.NONE, T3).strokes(6).radical(78)
            .leftRight(zi("歹"), zi("匕"))
            .compoundIndicative("die; death");

    public static final List<ChineseCharacterEntry> ALL = List.of(死_DIE_DEATH);
}
