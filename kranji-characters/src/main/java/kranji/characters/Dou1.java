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

/** Characters pronounced dou (tone 1). */
public final class Dou1 {
    private Dou1() {}

    /** 都 (dou1) — all; capital. */
    public static final ChineseCharacterEntry 都_ALL_CAPITAL = entry("都")
            .py(D, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(10).radical(163)
            .leftRight(zi("者"), YOU_ER_PANG)
            .phonoSemantic(YOU_ER_PANG, zi("者"));

    public static final List<ChineseCharacterEntry> ALL = List.of(都_ALL_CAPITAL);
}
