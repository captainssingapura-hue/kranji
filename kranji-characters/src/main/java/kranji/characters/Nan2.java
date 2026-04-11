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

/** Characters pronounced nan (tone 2). */
public final class Nan2 {
    private Nan2() {}

    /** 难 (nan2) — difficult. */
    public static final ChineseCharacterEntry 难_DIFFICULT = entry("难")
            .py(N, OPEN, Body.A, Tail.N, T2).strokes(10).radical(172)
            .leftRight(zi("又"), zi("隹"))
            .phonoSemantic(zi("隹"), zi("又"));

    /** 南 (nan2) — south. */
    public static final ChineseCharacterEntry 南_SOUTH = entry("南")
            .py(N, OPEN, Body.A, Tail.N, T2).strokes(9).radical(24)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(难_DIFFICULT, 南_SOUTH);
}
