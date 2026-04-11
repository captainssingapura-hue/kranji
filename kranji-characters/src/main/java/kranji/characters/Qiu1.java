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

/** Characters pronounced qiu (tone 1). */
public final class Qiu1 {
    private Qiu1() {}

    /** 秋 (qiu1) — autumn; fall. */
    public static final ChineseCharacterEntry 秋_AUTUMN_FALL = entry("秋")
            .py(Q, I, Body.O, Tail.VOWEL_U, T1).strokes(9).radical(115)
            .leftRight(zi("禾"), zi("火"))
            .compoundIndicative("autumn; fall");

    public static final List<ChineseCharacterEntry> ALL = List.of(秋_AUTUMN_FALL);
}
