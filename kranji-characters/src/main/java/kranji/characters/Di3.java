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

/** Characters pronounced di (tone 3). */
public final class Di3 {
    private Di3() {}

    /** 底 (di3) — bottom; base. */
    public static final ChineseCharacterEntry 底_BOTTOM_BASE = entry("底")
            .py(D, OPEN, Body.I, Tail.NONE, T3).strokes(8).radical(53)
            .semiEnclosureUL(zi("广"), zi("氐"))
            .phonoSemantic(zi("广"), zi("氐"));

    /** 抵 (di3) — resist; arrive. */
    public static final ChineseCharacterEntry 抵_RESIST_ARRIVE = entry("抵")
            .py(D, OPEN, Body.I, Tail.NONE, T3).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("氐"))
            .phonoSemantic(TI_SHOU_PANG, zi("氐"));

    public static final List<ChineseCharacterEntry> ALL = List.of(底_BOTTOM_BASE, 抵_RESIST_ARRIVE);
}
