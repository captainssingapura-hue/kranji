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

/** Characters pronounced yuan (tone 2). */
public final class Yuan2 {
    private Yuan2() {}

    /** 原 (yuan2) — original. */
    public static final ChineseCharacterEntry 原_ORIGINAL = entry("原")
            .py(ZERO, V, Body.A, Tail.N, T2).strokes(10).radical(27)
            .semiEnclosureUL(zi("厂"), zi("泉"))
            .phonoSemantic(zi("厂"), zi("泉"));

    /** 员 (yuan2) — member. */
    public static final ChineseCharacterEntry 员_MEMBER = entry("员")
            .py(ZERO, V, Body.A, Tail.N, T2).strokes(7).radical(30)
            .topBottom(zi("口"), zi("贝"))
            .phonoSemantic(zi("贝"), zi("口"));

    /** 园 (yuan2) — garden; park. */
    public static final ChineseCharacterEntry 园_GARDEN_PARK = entry("园")
            .py(ZERO, V, Body.A, Tail.N, T2).strokes(7).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("元"))
            .phonoSemantic(GUO_ZI_KUANG, zi("元"));

    /** 援 (yuan2) — aid; rescue. */
    public static final ChineseCharacterEntry 援_AID_RESCUE = entry("援")
            .py(ZERO, V, Body.A, Tail.N, T2).strokes(12).radical(64)
            .leftRight(TI_SHOU_PANG, zi("爰"))
            .phonoSemantic(TI_SHOU_PANG, zi("爰"));

    public static final List<ChineseCharacterEntry> ALL = List.of(原_ORIGINAL, 员_MEMBER, 园_GARDEN_PARK, 援_AID_RESCUE);
}
