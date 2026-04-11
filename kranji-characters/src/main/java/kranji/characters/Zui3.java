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

/** Characters pronounced zui (tone 3). */
public final class Zui3 {
    private Zui3() {}

    /** 嘴 (zui3) — mouth; beak. */
    public static final ChineseCharacterEntry 嘴_MOUTH_BEAK = entry("嘴")
            .py(Z, U, Body.E, Tail.VOWEL_I, T3).strokes(16).radical(30)
            .leftRight(zi("口"), zi("觜"))
            .phonoSemantic(zi("口"), zi("觜"));

    public static final List<ChineseCharacterEntry> ALL = List.of(嘴_MOUTH_BEAK);
}
