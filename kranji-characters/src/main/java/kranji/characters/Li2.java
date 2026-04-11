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

/** Characters pronounced li (tone 2). */
public final class Li2 {
    private Li2() {}

    /** 离 (li2) — leave; separate. */
    public static final ChineseCharacterEntry 离_LEAVE_SEPARATE = entry("离")
            .py(L, OPEN, Body.I, Tail.NONE, T2).strokes(10).radical(114)
            .singular()
            .compoundIndicative("leave; separate");

    public static final List<ChineseCharacterEntry> ALL = List.of(离_LEAVE_SEPARATE);
}
