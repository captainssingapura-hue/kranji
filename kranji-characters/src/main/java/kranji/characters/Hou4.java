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

/** Characters pronounced hou (tone 4). */
public final class Hou4 {
    private Hou4() {}

    /** 后 (hou4) — after; behind. */
    public static final ChineseCharacterEntry 后_AFTER_BEHIND = entry("后")
            .py(H, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(6).radical(30)
            .semiEnclosureUL(zi("𠂋"), zi("口"))
            .compoundIndicative("after; behind");

    /** 厚 (hou4) — thick; generous. */
    public static final ChineseCharacterEntry 厚_THICK_GENEROUS = entry("厚")
            .py(H, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(9).radical(27)
            .semiEnclosureUL(zi("厂"), zi("㫗"))
            .compoundIndicative("thick; generous");

    public static final List<ChineseCharacterEntry> ALL = List.of(后_AFTER_BEHIND, 厚_THICK_GENEROUS);
}
