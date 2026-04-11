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

/** Characters pronounced lian (tone 4). */
public final class Lian4 {
    private Lian4() {}

    /** 练 (lian4) — practice; drill. */
    public static final ChineseCharacterEntry 练_PRACTICE_DRILL = entry("练")
            .py(L, I, Body.A, Tail.N, T4).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("东"))
            .phonoSemantic(JIAO_SI_PANG, zi("东"));

    /** 恋 (lian4) — love; romance. */
    public static final ChineseCharacterEntry 恋_LOVE_ROMANCE = entry("恋")
            .py(L, I, Body.A, Tail.N, T4).strokes(10).radical(61)
            .topBottom(zi("亦"), zi("心"))
            .phonoSemantic(zi("心"), zi("亦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(练_PRACTICE_DRILL, 恋_LOVE_ROMANCE);
}
