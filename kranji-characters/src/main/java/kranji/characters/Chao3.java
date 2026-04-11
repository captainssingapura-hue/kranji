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

/** Characters pronounced chao (tone 3). */
public final class Chao3 {
    private Chao3() {}

    /** 吵 (chao3) — noisy; quarrel. */
    public static final ChineseCharacterEntry 吵_NOISY_QUARREL = entry("吵")
            .py(CH, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(7).radical(30)
            .leftRight(zi("口"), zi("少"))
            .phonoSemantic(zi("口"), zi("少"));

    public static final List<ChineseCharacterEntry> ALL = List.of(吵_NOISY_QUARREL);
}
