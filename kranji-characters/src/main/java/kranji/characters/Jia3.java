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

/** Characters pronounced jia (tone 3). */
public final class Jia3 {
    private Jia3() {}

    /** 甲 (jia3) — armor; first. */
    public static final ChineseCharacterEntry 甲_ARMOR_FIRST = entry("甲")
            .py(J, I, Body.A, Tail.NONE, T3).strokes(5).radical(102)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(甲_ARMOR_FIRST);
}
