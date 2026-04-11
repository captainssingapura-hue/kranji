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

/** Characters pronounced tu (tone 3). */
public final class Tu3 {
    private Tu3() {}

    /** 土 (tu3) — earth; soil. */
    public static final ChineseCharacterEntry 土_EARTH_SOIL = entry("土")
            .py(T, U, Body.U, Tail.NONE, T3).strokes(3).radical(32)
            .singular()
            .pictograph();

    /** 吐 (tu3) — spit; vomit. */
    public static final ChineseCharacterEntry 吐_SPIT_VOMIT = entry("吐")
            .py(T, U, Body.U, Tail.NONE, T3).strokes(6).radical(30)
            .leftRight(zi("口"), zi("土"))
            .phonoSemantic(zi("口"), zi("土"));

    public static final List<ChineseCharacterEntry> ALL = List.of(土_EARTH_SOIL, 吐_SPIT_VOMIT);
}
