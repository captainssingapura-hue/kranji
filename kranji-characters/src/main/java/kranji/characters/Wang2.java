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

/** Characters pronounced wang (tone 2). */
public final class Wang2 {
    private Wang2() {}

    /** 王 (wang2) — king. */
    public static final ChineseCharacterEntry 王_KING = entry("王")
            .py(ZERO, U, Body.A, Tail.NG, T2).strokes(4).radical(96)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(王_KING);
}
