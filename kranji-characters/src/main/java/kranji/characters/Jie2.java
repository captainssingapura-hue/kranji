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

/** Characters pronounced jie (tone 2). */
public final class Jie2 {
    private Jie2() {}

    /** 结 (jie2) — tie; result. */
    public static final ChineseCharacterEntry 结_TIE_RESULT = entry("结")
            .py(J, I, Body.E_CARON, Tail.NONE, T2).strokes(9).radical(120)
            .leftRight(JIAO_SI_PANG, zi("吉"))
            .phonoSemantic(JIAO_SI_PANG, zi("吉"));

    /** 节 (jie2) — festival; joint. */
    public static final ChineseCharacterEntry 节_FESTIVAL_JOINT = entry("节")
            .py(J, I, Body.E_CARON, Tail.NONE, T2).strokes(5).radical(140)
            .topBottom(CAO_ZI_TOU, zi("卩"))
            .compoundIndicative("festival; joint");

    public static final List<ChineseCharacterEntry> ALL = List.of(结_TIE_RESULT, 节_FESTIVAL_JOINT);
}
