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

/** Characters pronounced san (tone 1). */
public final class San1 {
    private San1() {}

    /** 三 (san1) — three. */
    public static final ChineseCharacterEntry 三_THREE = entry("三")
            .py(S, OPEN, Body.A, Tail.N, T1).strokes(3).radical(1)
            .singular()
            .indicative("three");

    public static final List<ChineseCharacterEntry> ALL = List.of(三_THREE);
}
