package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced guai (tone 3). */
public final class Guai3 {
    private Guai3() {}

    /** 拐 (guai3) — turn; crutch. */
    public static final ChineseCharacterEntry 拐_TURN_CRUTCH = entry("拐")
            .py(G, U, Body.A, Tail.VOWEL_I, T3).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("另"))
            .phonoSemantic(TI_SHOU_PANG, zi("另"));

    public static final List<ChineseCharacterEntry> ALL = List.of(拐_TURN_CRUTCH);
}
