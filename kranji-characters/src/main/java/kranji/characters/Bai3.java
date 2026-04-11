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

/** Characters pronounced bai (tone 3). */
public final class Bai3 {
    private Bai3() {}

    /** 白 (bai3) — white. */
    public static final ChineseCharacterEntry 白_WHITE = entry("白")
            .py(B, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(5).radical(106)
            .singular()
            .pictograph();

    /** 百 (bai3) — hundred. */
    public static final ChineseCharacterEntry 百_HUNDRED = entry("百")
            .py(B, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(6).radical(106)
            .topBottom(zi("一"), zi("白"))
            .phonoSemantic(zi("白"), zi("一"));

    public static final List<ChineseCharacterEntry> ALL = List.of(白_WHITE, 百_HUNDRED);
}
