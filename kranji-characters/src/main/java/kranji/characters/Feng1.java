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

/** Characters pronounced feng (tone 1). */
public final class Feng1 {
    private Feng1() {}

    /** 风 (feng1) — wind. */
    public static final ChineseCharacterEntry 风_WIND = entry("风")
            .py(F, OPEN, Body.E, Tail.NG, T1).strokes(4).radical(182)
            .singular()
            .pictograph();

    /** 丰 (feng1) — abundant; rich. */
    public static final ChineseCharacterEntry 丰_ABUNDANT_RICH = entry("丰")
            .py(F, OPEN, Body.E, Tail.NG, T1).strokes(4).radical(2)
            .singular()
            .pictograph();

    /** 蜂 (feng1) — bee; wasp. */
    public static final ChineseCharacterEntry 蜂_BEE_WASP = entry("蜂")
            .py(F, OPEN, Body.E, Tail.NG, T1).strokes(13).radical(142)
            .leftRight(zi("虫"), zi("丰"))
            .phonoSemantic(zi("虫"), zi("丰"));

    public static final List<ChineseCharacterEntry> ALL = List.of(风_WIND, 丰_ABUNDANT_RICH, 蜂_BEE_WASP);
}
