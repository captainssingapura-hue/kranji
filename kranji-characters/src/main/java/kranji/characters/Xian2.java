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

/** Characters pronounced xian (tone 2). */
public final class Xian2 {
    private Xian2() {}

    /** 咸 (xian2) — salty. */
    public static final ChineseCharacterEntry 咸_SALTY = entry("咸")
            .py(X, I, Body.A, Tail.N, T2).strokes(9).radical(30)
            .singular()
            .compoundIndicative("salty");

    public static final List<ChineseCharacterEntry> ALL = List.of(咸_SALTY);
}
