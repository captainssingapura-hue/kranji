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

/** Characters pronounced gan (tone 1). */
public final class Gan1 {
    private Gan1() {}

    /** 甘 (gan1) — sweet. */
    public static final ChineseCharacterEntry 甘_SWEET = entry("甘")
            .py(G, OPEN, Body.A, Tail.N, T1).strokes(5).radical(99)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(甘_SWEET);
}
