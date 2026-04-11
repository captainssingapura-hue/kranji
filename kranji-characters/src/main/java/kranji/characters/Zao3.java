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

/** Characters pronounced zao (tone 3). */
public final class Zao3 {
    private Zao3() {}

    /** 早 (zao3) — early; morning. */
    public static final ChineseCharacterEntry 早_EARLY_MORNING = entry("早")
            .py(Z, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(6).radical(72)
            .topBottom(zi("日"), zi("十"))
            .compoundIndicative("early; morning");

    public static final List<ChineseCharacterEntry> ALL = List.of(早_EARLY_MORNING);
}
