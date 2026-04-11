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

/** Characters pronounced ran (tone 3). */
public final class Ran3 {
    private Ran3() {}

    /** 染 (ran3) — dye; infect. */
    public static final ChineseCharacterEntry 染_DYE_INFECT = entry("染")
            .py(R, OPEN, Body.A, Tail.N, T3).strokes(9).radical(75)
            .topBottom(zi("氵九"), MU)
            .compoundIndicative("dye; infect");

    public static final List<ChineseCharacterEntry> ALL = List.of(染_DYE_INFECT);
}
