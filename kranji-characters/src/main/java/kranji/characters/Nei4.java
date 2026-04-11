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

/** Characters pronounced nei (tone 4). */
public final class Nei4 {
    private Nei4() {}

    /** 内 (nei4) — inside. */
    public static final ChineseCharacterEntry 内_INSIDE = entry("内")
            .py(N, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(4).radical(11)
            .singular()
            .compoundIndicative("inside");

    public static final List<ChineseCharacterEntry> ALL = List.of(内_INSIDE);
}
