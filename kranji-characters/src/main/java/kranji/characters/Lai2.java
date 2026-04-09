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

/** Characters pronounced lai (tone 2). */
public final class Lai2 {
    private Lai2() {}

    /** 来 (lai2) — come. */
    public static final ChineseCharacterEntry 来_COME = entry("来")
            .py(L, OPEN, Body.A, Tail.VOWEL_I, T2).strokes(7).radical(75)
            .topBottom(zi("一"), zi("米"))
            .loan("come");

    public static final List<ChineseCharacterEntry> ALL = List.of(来_COME);
}
