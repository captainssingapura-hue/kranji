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

/** Characters pronounced er (tone 4). */
public final class Er4 {
    private Er4() {}

    /** 二 (er4) — two. */
    public static final ChineseCharacterEntry 二_TWO = entry("二")
            .py(ZERO, OPEN, Body.ER, Tail.NONE, T4).strokes(2).radical(7)
            .singular()
            .indicative("two");

    public static final List<ChineseCharacterEntry> ALL = List.of(二_TWO);
}
