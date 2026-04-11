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

/** Characters pronounced mou (tone 3). */
public final class Mou3 {
    private Mou3() {}

    /** 某 (mou3) — certain; some. */
    public static final ChineseCharacterEntry 某_CERTAIN_SOME = entry("某")
            .py(M, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(9).radical(75)
            .topBottom(zi("甘"), MU)
            .compoundIndicative("certain; some");

    public static final List<ChineseCharacterEntry> ALL = List.of(某_CERTAIN_SOME);
}
