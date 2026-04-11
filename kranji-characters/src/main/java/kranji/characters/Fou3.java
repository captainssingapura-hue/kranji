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

/** Characters pronounced fou (tone 3). */
public final class Fou3 {
    private Fou3() {}

    /** 否 (fou3) — no; negate. */
    public static final ChineseCharacterEntry 否_NO_NEGATE = entry("否")
            .py(F, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(7).radical(30)
            .topBottom(zi("不"), zi("口"))
            .compoundIndicative("no; negate");

    public static final List<ChineseCharacterEntry> ALL = List.of(否_NO_NEGATE);
}
