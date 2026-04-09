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

/** Characters pronounced man (tone 3). */
public final class Man3 {
    private Man3() {}

    /** 满 (man3) — full; satisfy. */
    public static final ChineseCharacterEntry 满_FULL_SATISFY = entry("满")
            .py(M, OPEN, Body.A, Tail.N, T3).strokes(13).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("满"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("满"));

    public static final List<ChineseCharacterEntry> ALL = List.of(满_FULL_SATISFY);
}
