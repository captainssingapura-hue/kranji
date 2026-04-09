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

/** Characters pronounced bei (tone 3). */
public final class Bei3 {
    private Bei3() {}

    /** 北 (bei3) — north. */
    public static final ChineseCharacterEntry 北_NORTH = entry("北")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T3).strokes(5).radical(21)
            .leftRight(zi("匕"), zi("匕"))
            .compoundIndicative("north");

    public static final List<ChineseCharacterEntry> ALL = List.of(北_NORTH);
}
