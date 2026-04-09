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

/** Characters pronounced rou (tone 4). */
public final class Rou4 {
    private Rou4() {}

    /** 肉 (rou4) — meat. */
    public static final ChineseCharacterEntry 肉_MEAT = entry("肉")
            .py(R, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(6).radical(130)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(肉_MEAT);
}
