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

/** Characters pronounced jian (tone 4). */
public final class Jian4 {
    private Jian4() {}

    /** 见 (jian4) — see. */
    public static final ChineseCharacterEntry 见_SEE = entry("见")
            .py(J, I, Body.A, Tail.N, T4).strokes(4).radical(147)
            .singular()
            .pictograph();

    /** 件 (jian4) — item; piece. */
    public static final ChineseCharacterEntry 件_ITEM_PIECE = entry("件")
            .py(J, I, Body.A, Tail.N, T4).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("牛"))
            .phonoSemantic(DAN_REN_PANG, zi("牛"));

    /** 建 (jian4) — build. */
    public static final ChineseCharacterEntry 建_BUILD = entry("建")
            .py(J, I, Body.A, Tail.N, T4).strokes(8).radical(54)
            .semiEnclosureBL(JIAN_ZHI_PANG, zi("聿"))
            .phonoSemantic(JIAN_ZHI_PANG, zi("聿"));

    /** 践 (jian4) — practice; step. */
    public static final ChineseCharacterEntry 践_PRACTICE_STEP = entry("践")
            .py(J, I, Body.A, Tail.N, T4).strokes(12).radical(157)
            .leftRight(zi("足"), zi("戋"))
            .phonoSemantic(zi("足"), zi("戋"));

    /** 渐 (jian4) — gradually. */
    public static final ChineseCharacterEntry 渐_GRADUALLY = entry("渐")
            .py(J, I, Body.A, Tail.N, T4).strokes(11).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("斩"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("斩"));

    /** 剑 (jian4) — sword. */
    public static final ChineseCharacterEntry 剑_SWORD = entry("剑")
            .py(J, I, Body.A, Tail.N, T4).strokes(9).radical(18)
            .leftRight(zi("佥"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("佥"));

    /** 箭 (jian4) — arrow. */
    public static final ChineseCharacterEntry 箭_ARROW = entry("箭")
            .py(J, I, Body.A, Tail.N, T4).strokes(15).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("前"))
            .phonoSemantic(ZHU_ZI_TOU, zi("前"));

    public static final List<ChineseCharacterEntry> ALL = List.of(见_SEE, 件_ITEM_PIECE, 建_BUILD, 践_PRACTICE_STEP, 渐_GRADUALLY, 剑_SWORD, 箭_ARROW);
}
