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

/** Characters pronounced qiu (tone 2). */
public final class Qiu2 {
    private Qiu2() {}

    /** 求 (qiu2) — seek; request. */
    public static final ChineseCharacterEntry 求_SEEK_REQUEST = entry("求")
            .py(Q, I, Body.O, Tail.VOWEL_U, T2).strokes(7).radical(85)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(求_SEEK_REQUEST);
}
