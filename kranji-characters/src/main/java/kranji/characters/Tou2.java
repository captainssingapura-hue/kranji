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

/** Characters pronounced tou (tone 2). */
public final class Tou2 {
    private Tou2() {}

    /** 头 (tou2) — head. */
    public static final ChineseCharacterEntry 头_HEAD = entry("头")
            .py(T, OPEN, Body.O, Tail.VOWEL_U, T2).strokes(5).radical(37)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(头_HEAD);
}
