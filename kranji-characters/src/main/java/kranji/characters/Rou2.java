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

/** Characters pronounced rou (tone 2). */
public final class Rou2 {
    private Rou2() {}

    /** 柔 (rou2) — soft; gentle. */
    public static final ChineseCharacterEntry 柔_SOFT_GENTLE = entry("柔")
            .py(R, OPEN, Body.O, Tail.VOWEL_U, T2).strokes(9).radical(75)
            .topBottom(zi("矛"), MU)
            .compoundIndicative("soft; gentle");

    public static final List<ChineseCharacterEntry> ALL = List.of(柔_SOFT_GENTLE);
}
