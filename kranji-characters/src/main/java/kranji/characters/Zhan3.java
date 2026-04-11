package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced zhan (tone 3). */
public final class Zhan3 {
    private Zhan3() {}

    /** 展 (zhan3) — expand; show. */
    public static final ChineseCharacterEntry 展_EXPAND_SHOW = entry("展")
            .py(ZH, OPEN, Body.A, Tail.N, T3).strokes(10).radical(44)
            .semiEnclosureUL(zi("尸"), zi("展"))
            .compoundIndicative("expand; show");

    public static final List<ChineseCharacterEntry> ALL = List.of(展_EXPAND_SHOW);
}
