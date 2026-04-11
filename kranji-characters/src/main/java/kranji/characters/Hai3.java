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

/** Characters pronounced hai (tone 3). */
public final class Hai3 {
    private Hai3() {}

    /** 海 (hai3) — sea; ocean. */
    public static final ChineseCharacterEntry 海_SEA_OCEAN = entry("海")
            .py(H, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(10).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("每"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("每"));

    public static final List<ChineseCharacterEntry> ALL = List.of(海_SEA_OCEAN);
}
