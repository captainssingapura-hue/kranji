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

/** Characters pronounced zhong (tone 1). */
public final class Zhong1 {
    private Zhong1() {}

    /** 中 (zhong1) — middle. */
    public static final ChineseCharacterEntry 中_MIDDLE = entry("中")
            .py(ZH, OPEN, Body.O, Tail.NG, T1).strokes(4).radical(2)
            .singular()
            .pictograph();

    /** 终 (zhong1) — end; final. */
    public static final ChineseCharacterEntry 终_END_FINAL = entry("终")
            .py(ZH, OPEN, Body.O, Tail.NG, T1).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("冬"))
            .phonoSemantic(JIAO_SI_PANG, zi("冬"));

    /** 钟 (zhong1) — clock; bell. */
    public static final ChineseCharacterEntry 钟_CLOCK_BELL = entry("钟")
            .py(ZH, OPEN, Body.O, Tail.NG, T1).strokes(9).radical(167)
            .leftRight(JIN_ZI_PANG, zi("中"))
            .phonoSemantic(JIN_ZI_PANG, zi("中"));

    /** 忠 (zhong1) — loyal. */
    public static final ChineseCharacterEntry 忠_LOYAL = entry("忠")
            .py(ZH, OPEN, Body.O, Tail.NG, T1).strokes(8).radical(61)
            .topBottom(zi("中"), zi("心"))
            .phonoSemantic(zi("心"), zi("中"));

    public static final List<ChineseCharacterEntry> ALL = List.of(中_MIDDLE, 终_END_FINAL, 钟_CLOCK_BELL, 忠_LOYAL);
}
