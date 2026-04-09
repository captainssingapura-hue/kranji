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

/** Characters pronounced bi (tone 2). */
public final class Bi2 {
    private Bi2() {}

    /** 鼻 (bi2) — nose. */
    public static final ChineseCharacterEntry 鼻_NOSE = entry("鼻")
            .py(B, OPEN, Body.I, Tail.NONE, T2).strokes(14).radical(209)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(鼻_NOSE);
}
