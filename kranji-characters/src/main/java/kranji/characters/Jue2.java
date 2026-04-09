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

/** Characters pronounced jue (tone 2). */
public final class Jue2 {
    private Jue2() {}

    /** 觉 (jue2) — feel; awaken. */
    public static final ChineseCharacterEntry 觉_FEEL_AWAKEN = entry("觉")
            .py(J, V, Body.E_CARON, Tail.NONE, T2).strokes(9).radical(147)
            .topBottom(zi("⺍"), zi("见"))
            .phonoSemantic(zi("见"), zi("学"));

    /** 决 (jue2) — decide. */
    public static final ChineseCharacterEntry 决_DECIDE = entry("决")
            .py(J, V, Body.E_CARON, Tail.NONE, T2).strokes(6).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("夬"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("夬"));

    /** 绝 (jue2) — sever; absolute. */
    public static final ChineseCharacterEntry 绝_SEVER_ABSOLUTE = entry("绝")
            .py(J, V, Body.E_CARON, Tail.NONE, T2).strokes(9).radical(120)
            .leftRight(JIAO_SI_PANG, zi("色"))
            .phonoSemantic(JIAO_SI_PANG, zi("色"));

    public static final List<ChineseCharacterEntry> ALL = List.of(觉_FEEL_AWAKEN, 决_DECIDE, 绝_SEVER_ABSOLUTE);
}
