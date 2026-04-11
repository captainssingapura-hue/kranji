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

/** Characters pronounced pei (tone 2). */
public final class Pei2 {
    private Pei2() {}

    /** 赔 (pei2) — compensate. */
    public static final ChineseCharacterEntry 赔_COMPENSATE = entry("赔")
            .py(P, OPEN, Body.E, Tail.VOWEL_I, T2).strokes(12).radical(154)
            .leftRight(zi("贝"), zi("咅"))
            .phonoSemantic(zi("贝"), zi("咅"));

    /** 陪 (pei2) — accompany. */
    public static final ChineseCharacterEntry 陪_ACCOMPANY = entry("陪")
            .py(P, OPEN, Body.E, Tail.VOWEL_I, T2).strokes(10).radical(170)
            .leftRight(ZUO_ER_PANG, zi("咅"))
            .phonoSemantic(ZUO_ER_PANG, zi("咅"));

    /** 培 (pei2) — cultivate; train. */
    public static final ChineseCharacterEntry 培_CULTIVATE_TRAIN = entry("培")
            .py(P, OPEN, Body.E, Tail.VOWEL_I, T2).strokes(11).radical(32)
            .leftRight(zi("土"), zi("咅"))
            .phonoSemantic(zi("土"), zi("咅"));

    public static final List<ChineseCharacterEntry> ALL = List.of(赔_COMPENSATE, 陪_ACCOMPANY, 培_CULTIVATE_TRAIN);
}
