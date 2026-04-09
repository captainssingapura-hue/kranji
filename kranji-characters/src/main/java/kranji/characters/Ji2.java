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

/** Characters pronounced ji (tone 2). */
public final class Ji2 {
    private Ji2() {}

    /** 及 (ji2) — reach; and. */
    public static final ChineseCharacterEntry 及_REACH_AND = entry("及")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(3).radical(29)
            .singular()
            .compoundIndicative("reach; and");

    /** 即 (ji2) — immediately. */
    public static final ChineseCharacterEntry 即_IMMEDIATELY = entry("即")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(7).radical(26)
            .leftRight(zi("卩"), zi("艮"))
            .compoundIndicative("immediately");

    /** 集 (ji2) — gather; collect. */
    public static final ChineseCharacterEntry 集_GATHER_COLLECT = entry("集")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(12).radical(172)
            .topBottom(zi("隹"), zi("木"))
            .compoundIndicative("gather; collect");

    /** 极 (ji2) — extreme; pole. */
    public static final ChineseCharacterEntry 极_EXTREME_POLE = entry("极")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(7).radical(75)
            .leftRight(zi("木"), zi("及"))
            .phonoSemantic(zi("木"), zi("及"));

    /** 级 (ji2) — grade; level. */
    public static final ChineseCharacterEntry 级_GRADE_LEVEL = entry("级")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(6).radical(120)
            .leftRight(JIAO_SI_PANG, zi("及"))
            .phonoSemantic(JIAO_SI_PANG, zi("及"));

    /** 急 (ji2) — urgent; anxious. */
    public static final ChineseCharacterEntry 急_URGENT_ANXIOUS = entry("急")
            .py(J, OPEN, Body.I, Tail.NONE, T2).strokes(9).radical(61)
            .topBottom(zi("刍"), zi("心"))
            .phonoSemantic(zi("心"), zi("刍"));

    public static final List<ChineseCharacterEntry> ALL = List.of(及_REACH_AND, 即_IMMEDIATELY, 集_GATHER_COLLECT, 极_EXTREME_POLE, 级_GRADE_LEVEL, 急_URGENT_ANXIOUS);
}
