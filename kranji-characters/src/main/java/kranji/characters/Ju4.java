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

/** Characters pronounced ju (tone 4). */
public final class Ju4 {
    private Ju4() {}

    /** 据 (ju4) — according to. */
    public static final ChineseCharacterEntry 据_ACCORDING_TO = entry("据")
            .py(J, V, Body.V, Tail.NONE, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("居"))
            .phonoSemantic(TI_SHOU_PANG, zi("居"));

    /** 具 (ju4) — tool; possess. */
    public static final ChineseCharacterEntry 具_TOOL_POSSESS = entry("具")
            .py(J, V, Body.V, Tail.NONE, T4).strokes(8).radical(12)
            .singular()
            .compoundIndicative("tool; possess");

    /** 巨 (ju4) — huge; giant. */
    public static final ChineseCharacterEntry 巨_HUGE_GIANT = entry("巨")
            .py(J, V, Body.V, Tail.NONE, T4).strokes(4).radical(48)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(据_ACCORDING_TO, 具_TOOL_POSSESS, 巨_HUGE_GIANT);
}
