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

/** Characters pronounced er (tone 2). */
public final class Er2 {
    private Er2() {}

    /** 而 (er2) — and; but. */
    public static final ChineseCharacterEntry 而_AND_BUT = entry("而")
            .py(ZERO, OPEN, Body.ER, Tail.NONE, T2).strokes(6).radical(126)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(而_AND_BUT);
}
