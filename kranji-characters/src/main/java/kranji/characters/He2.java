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

/** Characters pronounced he (tone 2). */
public final class He2 {
    private He2() {}

    /** 和 (he2) — and; harmony. */
    public static final ChineseCharacterEntry 和_AND_HARMONY = entry("和")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(8).radical(115)
            .leftRight(zi("禾"), zi("口"))
            .phonoSemantic(zi("口"), zi("禾"));

    /** 合 (he2) — combine; fit. */
    public static final ChineseCharacterEntry 合_COMBINE_FIT = entry("合")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(6).radical(30)
            .topBottom(zi("人"), zi("口"))
            .compoundIndicative("combine; fit");

    /** 何 (he2) — what; how. */
    public static final ChineseCharacterEntry 何_WHAT_HOW = entry("何")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("可"))
            .phonoSemantic(DAN_REN_PANG, zi("可"));

    /** 河 (he2) — river. */
    public static final ChineseCharacterEntry 河_RIVER = entry("河")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("可"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("可"));

    /** 核 (he2) — core; nuclear. */
    public static final ChineseCharacterEntry 核_CORE_NUCLEAR = entry("核")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(10).radical(75)
            .leftRight(zi("木"), zi("亥"))
            .phonoSemantic(zi("木"), zi("亥"));

    /** 荷 (he2) — lotus. */
    public static final ChineseCharacterEntry 荷_LOTUS = entry("荷")
            .py(H, OPEN, Body.E, Tail.NONE, T2).strokes(10).radical(140)
            .topBottom(CAO_ZI_TOU, zi("何"))
            .phonoSemantic(CAO_ZI_TOU, zi("何"));

    public static final List<ChineseCharacterEntry> ALL = List.of(和_AND_HARMONY, 合_COMBINE_FIT, 何_WHAT_HOW, 河_RIVER, 核_CORE_NUCLEAR, 荷_LOTUS);
}
