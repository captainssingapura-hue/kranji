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

/** Characters pronounced wan (tone 4). */
public final class Wan4 {
    private Wan4() {}

    /** 万 (wan4) — ten thousand. */
    public static final ChineseCharacterEntry 万_TEN_THOUSAND = entry("万")
            .py(ZERO, U, Body.A, Tail.N, T4).strokes(3).radical(1)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(万_TEN_THOUSAND);
}
