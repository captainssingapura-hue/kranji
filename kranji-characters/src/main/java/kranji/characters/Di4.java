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

/** Characters pronounced di (tone 4). */
public final class Di4 {
    private Di4() {}

    /** 地 (di4) — earth; ground. */
    public static final ChineseCharacterEntry 地_EARTH_GROUND = entry("地")
            .py(D, OPEN, Body.I, Tail.NONE, T4).strokes(6).radical(32)
            .leftRight(zi("土"), zi("也"))
            .phonoSemantic(zi("土"), zi("也"));

    /** 第 (di4) — ordinal prefix. */
    public static final ChineseCharacterEntry 第_ORDINAL_PREFIX = entry("第")
            .py(D, OPEN, Body.I, Tail.NONE, T4).strokes(11).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("弟"))
            .phonoSemantic(ZHU_ZI_TOU, zi("弟"));

    /** 弟 (di4) — younger brother. */
    public static final ChineseCharacterEntry 弟_YOUNGER_BROTHER = entry("弟")
            .py(D, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(57)
            .singular()
            .compoundIndicative("younger brother");

    public static final List<ChineseCharacterEntry> ALL = List.of(地_EARTH_GROUND, 第_ORDINAL_PREFIX, 弟_YOUNGER_BROTHER);
}
