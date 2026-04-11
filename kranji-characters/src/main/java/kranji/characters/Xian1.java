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

/** Characters pronounced xian (tone 1). */
public final class Xian1 {
    private Xian1() {}

    /** 先 (xian1) — first; before. */
    public static final ChineseCharacterEntry 先_FIRST_BEFORE = entry("先")
            .py(X, I, Body.A, Tail.N, T1).strokes(6).radical(10)
            .topBottom(zi("𠂎"), zi("儿"))
            .compoundIndicative("first; before");

    /** 鲜 (xian1) — fresh. */
    public static final ChineseCharacterEntry 鲜_FRESH = entry("鲜")
            .py(X, I, Body.A, Tail.N, T1).strokes(14).radical(195)
            .leftRight(zi("鱼"), zi("羊"))
            .compoundIndicative("fresh");

    /** 仙 (xian1) — immortal. */
    public static final ChineseCharacterEntry 仙_IMMORTAL = entry("仙")
            .py(X, I, Body.A, Tail.N, T1).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("山"))
            .phonoSemantic(DAN_REN_PANG, zi("山"));

    public static final List<ChineseCharacterEntry> ALL = List.of(先_FIRST_BEFORE, 鲜_FRESH, 仙_IMMORTAL);
}
