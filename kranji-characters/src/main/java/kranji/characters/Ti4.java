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

/** Characters pronounced ti (tone 4). */
public final class Ti4 {
    private Ti4() {}

    /** 替 (ti4) — replace; for. */
    public static final ChineseCharacterEntry 替_REPLACE_FOR = entry("替")
            .py(T, OPEN, Body.I, Tail.NONE, T4).strokes(12).radical(73)
            .topBottom(zi("夫夫"), zi("日"))
            .compoundIndicative("replace; for");

    public static final List<ChineseCharacterEntry> ALL = List.of(替_REPLACE_FOR);
}
