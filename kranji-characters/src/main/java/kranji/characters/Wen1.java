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

/** Characters pronounced wen (tone 1). */
public final class Wen1 {
    private Wen1() {}

    /** 温 (wen1) — warm. */
    public static final ChineseCharacterEntry 温_WARM = entry("温")
            .py(ZERO, U, Body.E, Tail.N, T1).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("昷"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("昷"));

    public static final List<ChineseCharacterEntry> ALL = List.of(温_WARM);
}
