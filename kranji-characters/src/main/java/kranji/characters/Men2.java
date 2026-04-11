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

/** Characters pronounced men (tone 2). */
public final class Men2 {
    private Men2() {}

    /** 门 (men2) — door; gate. */
    public static final ChineseCharacterEntry 门_DOOR_GATE = entry("门")
            .py(M, OPEN, Body.E, Tail.N, T2).strokes(3).radical(169)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(门_DOOR_GATE);
}
