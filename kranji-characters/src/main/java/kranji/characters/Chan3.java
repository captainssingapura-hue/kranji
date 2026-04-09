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

/** Characters pronounced chan (tone 3). */
public final class Chan3 {
    private Chan3() {}

    /** 产 (chan3) — produce. */
    public static final ChineseCharacterEntry 产_PRODUCE = entry("产")
            .py(CH, OPEN, Body.A, Tail.N, T3).strokes(6).radical(8)
            .singular()
            .compoundIndicative("produce");

    public static final List<ChineseCharacterEntry> ALL = List.of(产_PRODUCE);
}
