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

/** Characters pronounced sui (tone 1). */
public final class Sui1 {
    private Sui1() {}

    /** 虽 (sui1) — although. */
    public static final ChineseCharacterEntry 虽_ALTHOUGH = entry("虽")
            .py(S, U, Body.E, Tail.VOWEL_I, T1).strokes(9).radical(142)
            .singular()
            .compoundIndicative("although");

    public static final List<ChineseCharacterEntry> ALL = List.of(虽_ALTHOUGH);
}
