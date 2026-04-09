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

/** Characters pronounced jiu (tone 3). */
public final class Jiu3 {
    private Jiu3() {}

    /** 九 (jiu3) — nine. */
    public static final ChineseCharacterEntry 九_NINE = entry("九")
            .py(J, I, Body.O, Tail.VOWEL_U, T3).strokes(2).radical(5)
            .singular()
            .pictograph();

    /** 酒 (jiu3) — alcohol; wine. */
    public static final ChineseCharacterEntry 酒_ALCOHOL_WINE = entry("酒")
            .py(J, I, Body.O, Tail.VOWEL_U, T3).strokes(10).radical(164)
            .leftRight(SAN_DIAN_SHUI, zi("酉"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("酉"));

    public static final List<ChineseCharacterEntry> ALL = List.of(九_NINE, 酒_ALCOHOL_WINE);
}
