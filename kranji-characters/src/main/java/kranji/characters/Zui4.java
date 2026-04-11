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

/** Characters pronounced zui (tone 4). */
public final class Zui4 {
    private Zui4() {}

    /** 最 (zui4) — most. */
    public static final ChineseCharacterEntry 最_MOST = entry("最")
            .py(Z, U, Body.E, Tail.VOWEL_I, T4).strokes(12).radical(73)
            .topBottom(zi("日"), zi("取"))
            .compoundIndicative("most");

    /** 醉 (zui4) — drunk. */
    public static final ChineseCharacterEntry 醉_DRUNK = entry("醉")
            .py(Z, U, Body.E, Tail.VOWEL_I, T4).strokes(15).radical(164)
            .leftRight(zi("酉"), zi("卒"))
            .phonoSemantic(zi("酉"), zi("卒"));

    public static final List<ChineseCharacterEntry> ALL = List.of(最_MOST, 醉_DRUNK);
}
