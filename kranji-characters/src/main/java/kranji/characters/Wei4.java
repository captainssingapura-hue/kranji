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

/** Characters pronounced wei (tone 4). */
public final class Wei4 {
    private Wei4() {}

    /** 为 (wei4) — for; do. */
    public static final ChineseCharacterEntry 为_FOR_DO = entry("为")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T4).strokes(4).radical(3)
            .singular()
            .pictograph();

    /** 位 (wei4) — position; seat. */
    public static final ChineseCharacterEntry 位_POSITION_SEAT = entry("位")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T4).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("立"))
            .phonoSemantic(DAN_REN_PANG, zi("立"));

    /** 味 (wei4) — taste; flavor. */
    public static final ChineseCharacterEntry 味_TASTE_FLAVOR = entry("味")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T4).strokes(8).radical(30)
            .leftRight(zi("口"), zi("未"))
            .phonoSemantic(zi("口"), zi("未"));

    /** 谓 (wei4) — say; mean. */
    public static final ChineseCharacterEntry 谓_SAY_MEAN = entry("谓")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T4).strokes(11).radical(149)
            .leftRight(YAN_ZI_PANG, zi("胃"))
            .phonoSemantic(YAN_ZI_PANG, zi("胃"));

    /** 卫 (wei4) — guard; defend. */
    public static final ChineseCharacterEntry 卫_GUARD_DEFEND = entry("卫")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T4).strokes(3).radical(144)
            .singular()
            .compoundIndicative("guard; defend");

    public static final List<ChineseCharacterEntry> ALL = List.of(为_FOR_DO, 位_POSITION_SEAT, 味_TASTE_FLAVOR, 谓_SAY_MEAN, 卫_GUARD_DEFEND);
}
