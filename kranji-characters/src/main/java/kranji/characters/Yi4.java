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

/** Characters pronounced yi (tone 4). */
public final class Yi4 {
    private Yi4() {}

    /** 意 (yi4) — meaning; idea. */
    public static final ChineseCharacterEntry 意_MEANING_IDEA = entry("意")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T4).strokes(13).radical(61)
            .topMiddleBottom(zi("立"), zi("日"), zi("心"))
            .compoundIndicative("meaning; idea");

    /** 义 (yi4) — justice; meaning. */
    public static final ChineseCharacterEntry 义_JUSTICE_MEANING = entry("义")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T4).strokes(3).radical(3)
            .singular()
            .indicative("justice; meaning");

    /** 议 (yi4) — discuss. */
    public static final ChineseCharacterEntry 议_DISCUSS = entry("议")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T4).strokes(5).radical(149)
            .leftRight(YAN_ZI_PANG, zi("义"))
            .phonoSemantic(YAN_ZI_PANG, zi("义"));

    /** 忆 (yi4) — remember. */
    public static final ChineseCharacterEntry 忆_REMEMBER = entry("忆")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T4).strokes(4).radical(61)
            .leftRight(SHU_XIN_PANG, zi("乙"))
            .phonoSemantic(SHU_XIN_PANG, zi("乙"));

    /** 亿 (yi4) — hundred million. */
    public static final ChineseCharacterEntry 亿_HUNDRED_MILLION = entry("亿")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T4).strokes(3).radical(9)
            .leftRight(DAN_REN_PANG, zi("乙"))
            .phonoSemantic(DAN_REN_PANG, zi("乙"));

    public static final List<ChineseCharacterEntry> ALL = List.of(意_MEANING_IDEA, 义_JUSTICE_MEANING, 议_DISCUSS, 忆_REMEMBER, 亿_HUNDRED_MILLION);
}
