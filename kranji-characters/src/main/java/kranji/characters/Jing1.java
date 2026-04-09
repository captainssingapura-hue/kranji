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

/** Characters pronounced jing (tone 1). */
public final class Jing1 {
    private Jing1() {}

    /** 经 (jing1) — pass through. */
    public static final ChineseCharacterEntry 经_PASS_THROUGH = entry("经")
            .py(J, OPEN, Body.I, Tail.NG, T1).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("巠"))
            .phonoSemantic(JIAO_SI_PANG, zi("巠"));

    /** 精 (jing1) — fine; essence. */
    public static final ChineseCharacterEntry 精_FINE_ESSENCE = entry("精")
            .py(J, OPEN, Body.I, Tail.NG, T1).strokes(14).radical(119)
            .leftRight(zi("米"), zi("青"))
            .phonoSemantic(zi("米"), zi("青"));

    /** 惊 (jing1) — surprise; shock. */
    public static final ChineseCharacterEntry 惊_SURPRISE_SHOCK = entry("惊")
            .py(J, OPEN, Body.I, Tail.NG, T1).strokes(11).radical(61)
            .leftRight(SHU_XIN_PANG, zi("京"))
            .phonoSemantic(SHU_XIN_PANG, zi("京"));

    public static final List<ChineseCharacterEntry> ALL = List.of(经_PASS_THROUGH, 精_FINE_ESSENCE, 惊_SURPRISE_SHOCK);
}
