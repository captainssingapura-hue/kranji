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

/** Characters pronounced ai (tone 4). */
public final class Ai4 {
    private Ai4() {}

    /** 爱 (ai4) — love. */
    public static final ChineseCharacterEntry 爱_LOVE = entry("爱")
            .py(ZERO, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(10).radical(87)
            .topBottom(zi("爫冖"), zi("友"))
            .compoundIndicative("love");

    public static final List<ChineseCharacterEntry> ALL = List.of(爱_LOVE);
}
