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

/** Characters pronounced ren (tone 2). */
public final class Ren2 {
    private Ren2() {}

    /** 人 (ren) — person. Singular pictograph. */
    public static final ChineseCharacterEntry 人_PERSON = entry("人")
            .py(R, OPEN, Body.E, Tail.N, T2).strokes(2).radical(9)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(人_PERSON);
}
