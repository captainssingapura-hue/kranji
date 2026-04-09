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

/** Characters pronounced nian (tone 2). */
public final class Nian2 {
    private Nian2() {}

    /** 年 (nian2) — year. */
    public static final ChineseCharacterEntry 年_YEAR = entry("年")
            .py(N, I, Body.A, Tail.N, T2).strokes(6).radical(51)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(年_YEAR);
}
