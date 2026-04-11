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

/** Characters pronounced kao (tone 3). */
public final class Kao3 {
    private Kao3() {}

    /** 考 (kao3) — test; examine. */
    public static final ChineseCharacterEntry 考_TEST_EXAMINE = entry("考")
            .py(K, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(6).radical(125)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(考_TEST_EXAMINE);
}
