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

/** Characters pronounced niu (tone 2). */
public final class Niu2 {
    private Niu2() {}

    /** 牛 (niu2) — cow; ox. */
    public static final ChineseCharacterEntry 牛_COW_OX = entry("牛")
            .py(N, I, Body.O, Tail.VOWEL_U, T2).strokes(4).radical(93)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(牛_COW_OX);
}
