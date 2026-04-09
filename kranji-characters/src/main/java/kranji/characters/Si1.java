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

/** Characters pronounced si (tone 1). */
public final class Si1 {
    private Si1() {}

    /** 思 (si1) — think; miss. */
    public static final ChineseCharacterEntry 思_THINK_MISS = entry("思")
            .py(S, OPEN, Body.NULL, Tail.NONE, T1).strokes(9).radical(61)
            .topBottom(zi("田"), zi("心"))
            .phonoSemantic(zi("心"), zi("田"));

    /** 斯 (si1) — this (literary). */
    public static final ChineseCharacterEntry 斯_THIS_LITERARY = entry("斯")
            .py(S, OPEN, Body.NULL, Tail.NONE, T1).strokes(12).radical(69)
            .leftRight(zi("其"), zi("斤"))
            .phonoSemantic(zi("斤"), zi("其"));

    /** 丝 (si1) — silk; thread. */
    public static final ChineseCharacterEntry 丝_SILK_THREAD = entry("丝")
            .py(S, OPEN, Body.NULL, Tail.NONE, T1).strokes(5).radical(120)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(思_THINK_MISS, 斯_THIS_LITERARY, 丝_SILK_THREAD);
}
