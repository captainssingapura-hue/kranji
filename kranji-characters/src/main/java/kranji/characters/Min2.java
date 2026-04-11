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

/** Characters pronounced min (tone 2). */
public final class Min2 {
    private Min2() {}

    /** 民 (min2) — people. */
    public static final ChineseCharacterEntry 民_PEOPLE = entry("民")
            .py(M, OPEN, Body.I, Tail.N, T2).strokes(5).radical(83)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(民_PEOPLE);
}
