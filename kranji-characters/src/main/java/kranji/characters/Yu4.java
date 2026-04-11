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

/** Characters pronounced yu (tone 4). */
public final class Yu4 {
    private Yu4() {}

    /** 育 (yu4) — nurture; educate. */
    public static final ChineseCharacterEntry 育_NURTURE_EDUCATE = entry("育")
            .py(ZERO, V, Body.V, Tail.NONE, T4).strokes(8).radical(130)
            .topBottom(WEN_ZI_TOU, zi("月"))
            .compoundIndicative("nurture; educate");

    /** 预 (yu4) — in advance. */
    public static final ChineseCharacterEntry 预_IN_ADVANCE = entry("预")
            .py(ZERO, V, Body.V, Tail.NONE, T4).strokes(10).radical(181)
            .leftRight(zi("予"), zi("页"))
            .phonoSemantic(zi("页"), zi("予"));

    /** 欲 (yu4) — desire; want. */
    public static final ChineseCharacterEntry 欲_DESIRE_WANT = entry("欲")
            .py(ZERO, V, Body.V, Tail.NONE, T4).strokes(11).radical(76)
            .leftRight(zi("谷"), zi("欠"))
            .phonoSemantic(zi("欠"), zi("谷"));

    /** 域 (yu4) — domain; area. */
    public static final ChineseCharacterEntry 域_DOMAIN_AREA = entry("域")
            .py(ZERO, V, Body.V, Tail.NONE, T4).strokes(11).radical(32)
            .leftRight(zi("土"), zi("或"))
            .phonoSemantic(zi("土"), zi("或"));

    public static final List<ChineseCharacterEntry> ALL = List.of(育_NURTURE_EDUCATE, 预_IN_ADVANCE, 欲_DESIRE_WANT, 域_DOMAIN_AREA);
}
