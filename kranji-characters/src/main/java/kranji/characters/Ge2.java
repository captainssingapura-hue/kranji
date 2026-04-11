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

/** Characters pronounced ge (tone 2). */
public final class Ge2 {
    private Ge2() {}

    /** 格 (ge2) — standard; grid. */
    public static final ChineseCharacterEntry 格_STANDARD_GRID = entry("格")
            .py(G, OPEN, Body.E, Tail.NONE, T2).strokes(10).radical(75)
            .leftRight(MU, zi("各"))
            .phonoSemantic(MU, zi("各"));

    /** 阁 (ge2) — pavilion; cabinet. */
    public static final ChineseCharacterEntry 阁_PAVILION_CABINET = entry("阁")
            .py(G, OPEN, Body.E, Tail.NONE, T2).strokes(9).radical(169)
            .semiEnclosureT3(zi("门"), zi("各"))
            .phonoSemantic(zi("门"), zi("各"));

    public static final List<ChineseCharacterEntry> ALL = List.of(格_STANDARD_GRID, 阁_PAVILION_CABINET);
}
