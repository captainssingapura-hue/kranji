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

/** Characters pronounced ai (tone 3). */
public final class Ai3 {
    private Ai3() {}

    /** 矮 (ai3) — short (height). */
    public static final ChineseCharacterEntry 矮_SHORT_HEIGHT = entry("矮")
            .py(ZERO, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(13).radical(111)
            .leftRight(zi("矢"), zi("委"))
            .phonoSemantic(zi("矢"), zi("委"));

    public static final List<ChineseCharacterEntry> ALL = List.of(矮_SHORT_HEIGHT);
}
