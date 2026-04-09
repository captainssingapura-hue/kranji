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

/** Characters pronounced dai (tone 4). */
public final class Dai4 {
    private Dai4() {}

    /** 带 (dai4) — belt; bring. */
    public static final ChineseCharacterEntry 带_BELT_BRING = entry("带")
            .py(D, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(9).radical(50)
            .topBottom(zi("带"), zi("巾"))
            .compoundIndicative("belt; bring");

    /** 代 (dai4) — generation; era. */
    public static final ChineseCharacterEntry 代_GENERATION_ERA = entry("代")
            .py(D, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("弋"))
            .phonoSemantic(DAN_REN_PANG, zi("弋"));

    /** 戴 (dai4) — wear (hat). */
    public static final ChineseCharacterEntry 戴_WEAR_HAT = entry("戴")
            .py(D, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(17).radical(62)
            .singular()
            .compoundIndicative("wear (hat)");

    public static final List<ChineseCharacterEntry> ALL = List.of(带_BELT_BRING, 代_GENERATION_ERA, 戴_WEAR_HAT);
}
