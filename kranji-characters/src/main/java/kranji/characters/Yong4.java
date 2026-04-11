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

/** Characters pronounced yong (tone 4). */
public final class Yong4 {
    private Yong4() {}

    /** 用 (yong4) — use. */
    public static final ChineseCharacterEntry 用_USE = entry("用")
            .py(ZERO, I, Body.O, Tail.NG, T4).strokes(5).radical(101)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(用_USE);
}
