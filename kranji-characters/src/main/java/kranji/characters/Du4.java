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

/** Characters pronounced du (tone 4). */
public final class Du4 {
    private Du4() {}

    /** 度 (du4) — degree; measure. */
    public static final ChineseCharacterEntry 度_DEGREE_MEASURE = entry("度")
            .py(D, U, Body.U, Tail.NONE, T4).strokes(9).radical(53)
            .semiEnclosureUL(zi("广"), zi("廿又"))
            .phonoSemantic(zi("广"), zi("度"));

    /** 肚 (du4) — belly. */
    public static final ChineseCharacterEntry 肚_BELLY = entry("肚")
            .py(D, U, Body.U, Tail.NONE, T4).strokes(7).radical(130)
            .leftRight(zi("月"), zi("土"))
            .phonoSemantic(zi("月"), zi("土"));

    public static final List<ChineseCharacterEntry> ALL = List.of(度_DEGREE_MEASURE, 肚_BELLY);
}
