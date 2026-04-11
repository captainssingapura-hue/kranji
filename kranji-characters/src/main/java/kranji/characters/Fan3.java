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

/** Characters pronounced fan (tone 3). */
public final class Fan3 {
    private Fan3() {}

    /** 反 (fan3) — opposite. */
    public static final ChineseCharacterEntry 反_OPPOSITE = entry("反")
            .py(F, OPEN, Body.A, Tail.N, T3).strokes(4).radical(29)
            .singular()
            .compoundIndicative("opposite");

    public static final List<ChineseCharacterEntry> ALL = List.of(反_OPPOSITE);
}
