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

/** Characters pronounced huo (tone 3). */
public final class Huo3 {
    private Huo3() {}

    /** 火 (huo) — fire. Singular pictograph. */
    public static final ChineseCharacterEntry 火_FIRE = entry("火")
            .py(H, U, Body.O, Tail.NONE, T3).strokes(4).radical(86)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(火_FIRE);
}
