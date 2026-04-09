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

/** Characters pronounced long (tone 2). */
public final class Long2 {
    private Long2() {}

    /** 龙 (long2) — dragon. */
    public static final ChineseCharacterEntry 龙_DRAGON = entry("龙")
            .py(L, OPEN, Body.O, Tail.NG, T2).strokes(5).radical(212)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(龙_DRAGON);
}
