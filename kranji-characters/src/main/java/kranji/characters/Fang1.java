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

/** Characters pronounced fang (tone 1). */
public final class Fang1 {
    private Fang1() {}

    /** 方 (fang1) — square; method. */
    public static final ChineseCharacterEntry 方_SQUARE_METHOD = entry("方")
            .py(F, OPEN, Body.A, Tail.NG, T1).strokes(4).radical(70)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(方_SQUARE_METHOD);
}
