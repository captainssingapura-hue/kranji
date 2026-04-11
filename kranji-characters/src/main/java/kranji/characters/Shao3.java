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

/** Characters pronounced shao (tone 3). */
public final class Shao3 {
    private Shao3() {}

    /** 少 (shao3) — few; little. */
    public static final ChineseCharacterEntry 少_FEW_LITTLE = entry("少")
            .py(SH, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(4).radical(42)
            .singular()
            .indicative("few; little");

    public static final List<ChineseCharacterEntry> ALL = List.of(少_FEW_LITTLE);
}
