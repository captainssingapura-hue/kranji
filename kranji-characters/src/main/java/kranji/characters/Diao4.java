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

/** Characters pronounced diao (tone 4). */
public final class Diao4 {
    private Diao4() {}

    /** 调 (diao4) — adjust; tone. */
    public static final ChineseCharacterEntry 调_ADJUST_TONE = entry("调")
            .py(D, I, Body.A, Tail.VOWEL_U, T4).strokes(10).radical(149)
            .leftRight(YAN_ZI_PANG, zi("周"))
            .phonoSemantic(YAN_ZI_PANG, zi("周"));

    /** 掉 (diao4) — drop; fall. */
    public static final ChineseCharacterEntry 掉_DROP_FALL = entry("掉")
            .py(D, I, Body.A, Tail.VOWEL_U, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("卓"))
            .phonoSemantic(TI_SHOU_PANG, zi("卓"));

    public static final List<ChineseCharacterEntry> ALL = List.of(调_ADJUST_TONE, 掉_DROP_FALL);
}
