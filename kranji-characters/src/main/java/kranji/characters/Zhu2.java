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

/** Characters pronounced zhu (tone 2). */
public final class Zhu2 {
    private Zhu2() {}

    /** 逐 (zhu2) — chase; one by one. */
    public static final ChineseCharacterEntry 逐_CHASE_ONE_BY_ONE = entry("逐")
            .py(ZH, U, Body.U, Tail.NONE, T2).strokes(10).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("豕"))
            .compoundIndicative("chase; one by one");

    /** 竹 (zhu2) — bamboo. */
    public static final ChineseCharacterEntry 竹_BAMBOO = entry("竹")
            .py(ZH, U, Body.U, Tail.NONE, T2).strokes(6).radical(118)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(逐_CHASE_ONE_BY_ONE, 竹_BAMBOO);
}
