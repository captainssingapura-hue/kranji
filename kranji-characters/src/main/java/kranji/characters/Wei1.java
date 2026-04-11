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

/** Characters pronounced wei (tone 1). */
public final class Wei1 {
    private Wei1() {}

    /** 微 (wei1) — tiny; micro. */
    public static final ChineseCharacterEntry 微_TINY_MICRO = entry("微")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T1).strokes(13).radical(60)
            .leftMiddleRight(SHUANG_REN_PANG, zi("山"), zi("几"))
            .phonoSemantic(SHUANG_REN_PANG, zi("微"));

    /** 危 (wei1) — danger. */
    public static final ChineseCharacterEntry 危_DANGER = entry("危")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T1).strokes(6).radical(26)
            .topBottom(zi("⺈"), zi("厄"))
            .compoundIndicative("danger");

    /** 威 (wei1) — power; prestige. */
    public static final ChineseCharacterEntry 威_POWER_PRESTIGE = entry("威")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T1).strokes(9).radical(62)
            .singular()
            .compoundIndicative("power; prestige");

    public static final List<ChineseCharacterEntry> ALL = List.of(微_TINY_MICRO, 危_DANGER, 威_POWER_PRESTIGE);
}
