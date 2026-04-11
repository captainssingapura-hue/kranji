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

/** Characters pronounced mian (tone 3). */
public final class Mian3 {
    private Mian3() {}

    /** 免 (mian3) — avoid; free. */
    public static final ChineseCharacterEntry 免_AVOID_FREE = entry("免")
            .py(M, I, Body.A, Tail.N, T3).strokes(7).radical(10)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(免_AVOID_FREE);
}
