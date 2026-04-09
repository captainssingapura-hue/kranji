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

/** Characters pronounced mei (tone 3). */
public final class Mei3 {
    private Mei3() {}

    /** 美 (mei3) — beautiful. */
    public static final ChineseCharacterEntry 美_BEAUTIFUL = entry("美")
            .py(M, OPEN, Body.E, Tail.VOWEL_I, T3).strokes(9).radical(123)
            .topBottom(zi("羊"), zi("大"))
            .compoundIndicative("beautiful");

    /** 每 (mei3) — every; each. */
    public static final ChineseCharacterEntry 每_EVERY_EACH = entry("每")
            .py(M, OPEN, Body.E, Tail.VOWEL_I, T3).strokes(7).radical(80)
            .topBottom(zi("𠂉"), zi("母"))
            .compoundIndicative("every; each");

    public static final List<ChineseCharacterEntry> ALL = List.of(美_BEAUTIFUL, 每_EVERY_EACH);
}
