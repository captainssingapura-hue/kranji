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

/** Characters pronounced you (tone 3). */
public final class You3 {
    private You3() {}

    /** 有 (you3) — have. */
    public static final ChineseCharacterEntry 有_HAVE = entry("有")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T3).strokes(6).radical(74)
            .topBottom(zi("𠂇"), zi("月"))
            .compoundIndicative("have");

    public static final List<ChineseCharacterEntry> ALL = List.of(有_HAVE);
}
