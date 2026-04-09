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

/** Characters pronounced shao (tone 2). */
public final class Shao2 {
    private Shao2() {}

    /** 勺 (shao2) — spoon. */
    public static final ChineseCharacterEntry 勺_SPOON = entry("勺")
            .py(SH, OPEN, Body.A, Tail.VOWEL_U, T2).strokes(3).radical(20)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(勺_SPOON);
}
