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

/** Characters pronounced qi (tone 2). */
public final class Qi2 {
    private Qi2() {}

    /** 其 (qi2) — its; that. */
    public static final ChineseCharacterEntry 其_ITS_THAT = entry("其")
            .py(Q, OPEN, Body.I, Tail.NONE, T2).strokes(8).radical(12)
            .singular()
            .pictograph();

    /** 骑 (qi2) — ride (horse). */
    public static final ChineseCharacterEntry 骑_RIDE_HORSE = entry("骑")
            .py(Q, OPEN, Body.I, Tail.NONE, T2).strokes(11).radical(187)
            .leftRight(zi("马"), zi("奇"))
            .phonoSemantic(zi("马"), zi("奇"));

    /** 奇 (qi2) — strange; wonder. */
    public static final ChineseCharacterEntry 奇_STRANGE_WONDER = entry("奇")
            .py(Q, OPEN, Body.I, Tail.NONE, T2).strokes(8).radical(37)
            .topBottom(zi("大"), zi("可"))
            .phonoSemantic(zi("大"), zi("可"));

    /** 旗 (qi2) — flag; banner. */
    public static final ChineseCharacterEntry 旗_FLAG_BANNER = entry("旗")
            .py(Q, OPEN, Body.I, Tail.NONE, T2).strokes(14).radical(70)
            .leftRight(zi("方"), zi("其"))
            .phonoSemantic(zi("方"), zi("其"));

    public static final List<ChineseCharacterEntry> ALL = List.of(其_ITS_THAT, 骑_RIDE_HORSE, 奇_STRANGE_WONDER, 旗_FLAG_BANNER);
}
